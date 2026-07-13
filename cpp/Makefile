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

VERSION    = 0.8.7-beta.1
SPEC       = ../docs/src/spec.yaml
NPROC     := $(shell nproc 2>/dev/null || sysctl -n hw.logicalcpu 2>/dev/null || echo 8)
CLANG_FMT ?= clang-format-20

# ─── REST client (cpp-restsdk, generated) ────────────────────────────────────

.PHONY: clean-rest-client
clean-rest-client:
	rm -rf lance_namespace_rest_client

.PHONY: gen-rest-client
gen-rest-client: clean-rest-client
	uv run openapi-generator-cli generate \
	  -i $(SPEC) \
	  -g cpp-restsdk \
	  -o lance_namespace_rest_client \
	  --additional-properties=packageName=LanceNamespace,packageVersion=$(VERSION),httpUserAgent=lance-namespace-cpp/$(VERSION)
	rm -rf lance_namespace_rest_client/.openapi-generator
	rm -f  lance_namespace_rest_client/.gitignore
	rm -f  lance_namespace_rest_client/git_push.sh
	@echo "REST client generated. Update lance_namespace_rest_client/include/lance_namespace_rest_client/lance_namespace.hpp manually if new models were added."

# ─── Core build ──────────────────────────────────────────────────────────────

.PHONY: build-core
build-core: gen-rest-client
	cmake -S lance_namespace -B lance_namespace/build -DCMAKE_BUILD_TYPE=Release
	cmake --build lance_namespace/build --parallel $(NPROC)

.PHONY: test-core
test-core: build-core
	cd lance_namespace/build && ctest --output-on-failure -j$(NPROC)

# ─── clang-format ────────────────────────────────────────────────────────────

SOURCES := $(shell find lance_namespace/include lance_namespace/src lance_namespace/tests \
             -name '*.cpp' -o -name '*.h' -o -name '*.hpp' 2>/dev/null)

.PHONY: format
format:
	$(CLANG_FMT) -i $(SOURCES)

.PHONY: format-check
format-check:
	@DIFF=$$($(CLANG_FMT) --dry-run --Werror $(SOURCES) 2>&1); \
	if [ -n "$$DIFF" ]; then \
	  echo "$$DIFF"; \
	  echo "Run 'make format' to fix formatting."; \
	  exit 1; \
	fi
	@echo "All files pass clang-format-20."

.PHONY: clean
clean: clean-rest-client
	rm -rf lance_namespace/build

.PHONY: gen
gen: gen-rest-client

.PHONY: build
build: build-core

.PHONY: test
test: test-core

