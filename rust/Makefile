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

VERSION = 0.0.1

clean-rust-reqwest-client:
	rm -rf lance-catalog-reqwest-client/**

gen-rust-reqwest-client: clean-rust-reqwest-client
	openapi-generator-cli generate \
	  -i ../spec/rest.yaml \
	  -g rust \
	  -o lance-catalog-reqwest-client \
	  --additional-properties=packageName=lance-catalog-reqwest-client,packageVersion=$(VERSION),asyncApi=true,library=reqwest

build-rust-reqwest-client: gen-rust-reqwest-client
	cargo test

clean: clean-rust-reqwest-client

gen: gen-rust-reqwest-client

build: build-rust-reqwest-client