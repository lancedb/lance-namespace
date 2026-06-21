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

VERSION = 0.8.7-beta.1
BUILD_DIR ?= build
GENERATED_DIR ?= lance_namespace_rest_client
DIST_DIR ?= dist
JOBS ?= $(shell nproc 2>/dev/null || echo 1)

.PHONY: clean-rest-client
clean-rest-client:
	rm -rf $(GENERATED_DIR)

.PHONY: gen-rest-client
gen-rest-client: clean-rest-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/spec.yaml \
	  -g cpp-restsdk \
	  -o $(GENERATED_DIR) \
	  --additional-properties=packageName=lance_namespace_rest_client,packageVersion=$(VERSION);
	uv run python -c "from pathlib import Path; p = Path('$(GENERATED_DIR)/CMakeLists.txt'); text = p.read_text(); text = text.replace('cmake_minimum_required (VERSION 3.5)', 'cmake_minimum_required (VERSION 3.20)'); text = text.replace('set(CXX_STANDARD_REQUIRED ON)', 'set(CMAKE_CXX_STANDARD_REQUIRED ON)\\nset(CMAKE_CXX_STANDARD 17)'); p.write_text(text)"
	rm -rf $(GENERATED_DIR)/.openapi-generator
	rm -f $(GENERATED_DIR)/.gitignore $(GENERATED_DIR)/git_push.sh $(GENERATED_DIR)/.openapi-generator-ignore

.PHONY: build-rest-client
build-rest-client: gen-rest-client
	cd $(GENERATED_DIR); \
		cmake -S . -B $(BUILD_DIR) -DCMAKE_BUILD_TYPE=Release; \
		cmake --build $(BUILD_DIR) --parallel $(JOBS); \
		ctest --test-dir $(BUILD_DIR) --output-on-failure --parallel $(JOBS);

# lance_namespace package (hand-written, no codegen)
.PHONY: clean-core
clean-core:
	cd lance_namespace; \
		rm -rf $(BUILD_DIR) $(DIST_DIR);

.PHONY: build-core
build-core:
	cd lance_namespace; \
		cmake -S . -B $(BUILD_DIR) -DCMAKE_BUILD_TYPE=Release; \
		cmake --build $(BUILD_DIR) --parallel $(JOBS); \
		ctest --test-dir $(BUILD_DIR) --output-on-failure --parallel $(JOBS);


.PHONY: clean
clean: clean-rest-client clean-core

.PHONY: gen
gen: gen-rest-client

.PHONY: build
build: build-rest-client build-core
	

.PHONY: publish
publish: build
	uv run python package_release.py \
	  --root . \
	  --output $(DIST_DIR)/lance-namespace-cpp-$(VERSION).tar.gz \
	  --build-dir $(BUILD_DIR) \
	  --dist-dir $(DIST_DIR);
