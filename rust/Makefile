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

VERSION = 0.0.9

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
	cargo test

.PHONY: lint-ns
lint-ns:
	cd lance-namespace && cargo fmt
	cd lance-namespace && cargo clippy --all-targets --all-features

.PHONY: build-ns
build-ns:
	cd lance-namespace && cargo test

.PHONY: clean
clean: clean-reqwest-client

.PHONY: gen
gen: gen-reqwest-client

.PHONY: build
build: build-reqwest-client lint-ns build-ns