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

VERSION = 0.0.3

.PHONY: clean-rust-reqwest-client
clean-rust-reqwest-client:
	rm -rf lance-namespace-reqwest-client/**

.PHONY: gen-rust-reqwest-client
gen-rust-reqwest-client: clean-rust-reqwest-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/spec/rest.yaml \
	  -g rust \
	  -o lance-namespace-reqwest-client \
	  --type-mappings='Binary=Vec<u8>,binary=Vec<u8>,file=Vec<u8>' \
	  --additional-properties=packageName=lance-namespace-reqwest-client,packageVersion=$(VERSION),asyncApi=true,library=reqwest
	rm -rf lance-namespace-reqwest-client/.openapi-generator-ignore
	rm -rf lance-namespace-reqwest-client/.openapi-generator
	rm -rf lance-namespace-reqwest-client/.gitignore
	rm -rf lance-namespace-reqwest-client/.travis.yml
	rm -rf lance-namespace-reqwest-client/git_push.sh

.PHONY: build-rust-reqwest-client
build-rust-reqwest-client: gen-rust-reqwest-client
	cargo test

.PHONY: clean
clean: clean-rust-reqwest-client

.PHONY: gen
gen: gen-rust-reqwest-client

.PHONY: build
build: build-rust-reqwest-client