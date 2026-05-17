# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

.PHONY: lint
lint:
	uv run openapi-spec-validator --errors all docs/src/spec.yaml

.PHONY: clean-rust
clean-rust:
	cd rust; make clean

.PHONY: sync gen-rust
gen-rust:
	cd rust; make gen

.PHONY: build-rust
build-rust:
	cd rust; make build

.PHONY: clean-python
clean-python:
	cd python; make clean

.PHONY: sync gen-python
gen-python:
	cd python; make gen

.PHONY: build-python
build-python:
	cd python; make build

.PHONY: clean-java
clean-java:
	cd java; make clean

.PHONY: gen-java
gen-java:
	cd java; make gen

.PHONY: build-java
build-java:
	cd java; make build

.PHONY: build-docs
build-docs: gen-java
	cd docs; make build

.PHONY: serve-docs
serve-docs: gen-java
	cd docs; make serve

.PHONY: sync
sync:
	uv sync --all-packages

.PHONY: clean
clean: clean-rust clean-python clean-java

.PHONY: gen
gen: lint gen-rust gen-python gen-java

.PHONY: build
build: lint build-docs build-rust build-python build-java build-cts

# ============================================================
# CTS (Contract Test Suite) targets
# ============================================================

# Variables
SPEC_SRC          := docs/src/spec.yaml
AUTO_OVERLAY      := build/overlays/examples.auto.yaml
SPEC_MERGED       := build/spec.merged.yaml
CTS_OUT           := build/cts
WIREMOCK_VER      := 3.9.1
WIREMOCK_JAR      := $(CTS_OUT)/wiremock-standalone.jar
WIREMOCK_MAPPINGS := $(CTS_OUT)/wiremock/src/main/resources/mappings

# Generate examples overlay from spec (read-only on spec)
$(AUTO_OVERLAY): $(SPEC_SRC)
	@mkdir -p build/overlays
	uv run python ci/cts/gen_examples_overlay.py \
		--spec $(SPEC_SRC) \
		--output $(AUTO_OVERLAY)

# Merge spec with overlay to produce annotated spec
$(SPEC_MERGED): $(AUTO_OVERLAY) $(SPEC_SRC)
	@mkdir -p build
	uv run python ci/cts/apply_overlay.py \
		$(SPEC_SRC) $(AUTO_OVERLAY) > $(SPEC_MERGED)

merge-spec: $(SPEC_MERGED)
.PHONY: merge-spec

# Generate WireMock stubs from spec for all operations.
# gen_wiremock_mappings.py reads spec.yaml and produces one JSON mapping per
# operation (49 total).  Requires only pyyaml (already a workspace dependency).
gen-wiremock: $(SPEC_MERGED)
	@mkdir -p $(WIREMOCK_MAPPINGS)
	uv run python ci/cts/gen_wiremock_mappings.py \
		--spec $(SPEC_SRC) \
		--output $(WIREMOCK_MAPPINGS)
	@ls $(WIREMOCK_MAPPINGS)/*.json >/dev/null 2>&1 || \
		(echo "ERROR: gen_wiremock_mappings.py produced no mapping files" && exit 1)

.PHONY: gen-wiremock

# Download WireMock standalone jar
$(WIREMOCK_JAR):
	@mkdir -p $(CTS_OUT)
	curl -fsSL \
	  https://repo1.maven.org/maven2/org/wiremock/wiremock-standalone/$(WIREMOCK_VER)/wiremock-standalone-$(WIREMOCK_VER).jar \
	  -o $(WIREMOCK_JAR)

# Generate client contract test files for all 4 clients (Rust, Python, Java Apache, Java Async).
# Depends on gen-wiremock so the mappings directory already exists when the script runs.
gen-client-tests: gen-wiremock
	uv run python ci/cts/gen_client_tests.py \
		--mappings-dir $(WIREMOCK_MAPPINGS) \
		--out-rust rust/lance-namespace-reqwest-client/tests/contract.rs \
		--out-python python/lance_namespace_urllib3_client/tests/test_contract.py \
		--out-java-apache java/lance-namespace-apache-client/src/test/java/org/lance/namespace/client/apache/cts/WireMockContractIT.java \
		--out-java-async java/lance-namespace-async-client/src/test/java/org/lance/namespace/client/async/cts/WireMockContractIT.java
	# Apply spotless formatting to the generated Java test files so the committed
	# version satisfies spotless:check on CI (which does not run apply beforehand).
	cd java && ./mvnw -q spotless:apply \
		-pl lance-namespace-apache-client,lance-namespace-async-client -am \
		-DskipTests || true

.PHONY: gen-client-tests

# Run all CTS generation steps
gen-cts: $(AUTO_OVERLAY) $(SPEC_MERGED) gen-wiremock gen-client-tests $(WIREMOCK_JAR)
	@echo "CTS artifacts generated in $(CTS_OUT)"

.PHONY: gen-cts

# Regenerate CTS artifacts (spec merge, WireMock stubs, per-client contract
# tests) AFTER the clients have been (re)generated and built.
#
# Order matters: each per-language `build-*` target depends on a `gen-*-client`
# target that internally does `rm -rf <client-dir>/**` followed by
# openapi-generator.  If we ran `gen-cts` first, the generated contract test
# files (which live inside those very client directories) would be wiped out
# moments later.  Therefore: build clients first, then drop the contract test
# files into the freshly produced trees.
#
# Finally, run `test-compile` on the two Java client modules so the freshly
# written `WireMockContractIT.java` is actually compiled (the per-module
# `build` targets above only ran `mvn install`, which happens *before* the
# contract tests are written).
build-cts: build-rust build-python build-java gen-cts
	cd java && ./mvnw -q -pl lance-namespace-apache-client,lance-namespace-async-client \
		test-compile -DskipTests --no-transfer-progress
	@echo "CTS build complete"

.PHONY: build-cts

# Spec lint
test-spec-lint: verify-spec-untouched
	@mkdir -p build
	npx --yes @stoplight/spectral-cli lint $(SPEC_SRC) \
		--ruleset ci/spectral.yaml \
		--fail-severity error \
		--format junit \
		--output build/spectral-report.xml

.PHONY: test-spec-lint

# Schemathesis server conformance test
# Requires: BASE_URL env var pointing to a running server, and ci/schemathesis.toml
test-schemathesis: $(SPEC_MERGED)
	@mkdir -p build/reports
	uv run schemathesis --config-file ci/schemathesis.toml run $(SPEC_MERGED) \
		--url $${BASE_URL:-http://localhost:8080} \
		--report-junit-path build/reports/schemathesis.xml

.PHONY: test-schemathesis

# Java client contract tests
test-client-java: gen-wiremock $(WIREMOCK_JAR)
	cd java && ./mvnw -pl lance-namespace-apache-client,lance-namespace-async-client \
		test -Dtest="WireMockContractIT" \
		-Dsurefire.failIfNoSpecifiedTests=false \
		--no-transfer-progress

.PHONY: test-client-java

# Python client contract tests
test-client-python: gen-wiremock $(WIREMOCK_JAR)
	cd python && uv run pytest \
		lance_namespace_urllib3_client/tests/test_contract.py \
		-v --tb=short

.PHONY: test-client-python

# Rust client contract tests
test-client-rust: gen-wiremock $(WIREMOCK_JAR)
	cd rust/lance-namespace-reqwest-client && \
		cargo test --test contract -- --nocapture

.PHONY: test-client-rust

# All client tests
test-clients: test-client-java test-client-python test-client-rust
.PHONY: test-clients

# All CTS tests (client-side; add test-schemathesis when a server is running)
test-cts: test-spec-lint build-cts test-clients
.PHONY: test-cts

# Verify spec was not modified
verify-spec-untouched:
	@git diff --exit-code -- $(SPEC_SRC) || \
		(echo "ERROR: $(SPEC_SRC) was modified. This file must not be changed." && exit 1)
	@echo "OK: $(SPEC_SRC) is untouched"

.PHONY: verify-spec-untouched

