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

VERSION = 0.0.6

.PHONY: clean-reqwest-client
clean-reqwest-client:
	rm -rf lance-namespace-reqwest-client/**

.PHONY: gen-reqwest-client
gen-reqwest-client: clean-reqwest-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/rest.yaml \
	  -g rust \
	  -o lance-namespace-reqwest-client \
	  --type-mappings='Binary=Vec<u8>,binary=Vec<u8>,file=Vec<u8>' \
	  --additional-properties=packageName=lance-namespace-reqwest-client,packageVersion=$(VERSION),asyncApi=true,library=reqwest
	rm -rf lance-namespace-reqwest-client/.openapi-generator-ignore
	rm -rf lance-namespace-reqwest-client/.openapi-generator
	rm -rf lance-namespace-reqwest-client/.gitignore
	rm -rf lance-namespace-reqwest-client/.travis.yml
	rm -rf lance-namespace-reqwest-client/git_push.sh

.PHONY: build-reqwest-client
build-reqwest-client: gen-reqwest-client
	cd lance-namespace-reqwest-client && cargo build

# Lance namespace specific targets
.PHONY: format-check
format-check:
	cargo fmt --all -- --check

.PHONY: format
format:
	cargo fmt --all

.PHONY: lint-lance-namespace
lint-lance-namespace:
	cd lance-namespace && cargo clippy --all-targets --all-features 2>&1 | grep -v "lance-namespace-reqwest-client" || true

.PHONY: lint-lance-namespace-strict
lint-lance-namespace-strict:
	cd lance-namespace && cargo clippy --all-targets --all-features -- -D warnings

.PHONY: lint-all
lint-all:
	cargo clippy --all --all-targets --all-features

.PHONY: build-lance-namespace
build-lance-namespace:
	cd lance-namespace && cargo build --all-features

.PHONY: test-lance-namespace
test-lance-namespace:
	cd lance-namespace && cargo test --all-features

.PHONY: test-all
test-all:
	cargo test --all

# Convenience targets
.PHONY: clean
clean: clean-reqwest-client
	cargo clean

.PHONY: gen
gen: gen-reqwest-client

.PHONY: check
check: format-check lint-lance-namespace build-lance-namespace test-lance-namespace

.PHONY: build
build: build-reqwest-client build-lance-namespace

.PHONY: test
test: test-lance-namespace

.PHONY: lint
lint: lint-lance-namespace