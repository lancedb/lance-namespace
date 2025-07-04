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

clean-python-urllib3-client:
	rm -rf lance_namespace_urllib3_client/**

gen-python-urllib3-client: clean-python-urllib3-client
	openapi-generator-cli generate \
	  -i ../docs/src/spec/rest.yaml \
	  -g python \
	  -o lance_namespace_urllib3_client \
	  --additional-properties=packageName=lance_namespace_urllib3_client,packageVersion=$(VERSION),library=urllib3 \
	# Clean up OpenAPI Generator metadata after generation
	rm -rf lance_namespace_urllib3_client/.openapi-generator

build-python-urllib3-client: gen-python-urllib3-client
	cd lance_namespace_urllib3_client; \
		poetry install; \
		poetry run pytest

clean: clean-python-urllib3-client

gen: gen-python-urllib3-client

build: build-python-urllib3-client
