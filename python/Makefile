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

VERSION = 0.0.2

clean-python-urllib3-client:
	rm -rf lance_namespace_urllib3_client/**
	mkdir -p lance_namespace_urllib3_client
	cp pyproject.urllib3_client.toml lance_namespace_urllib3_client/pyproject.toml

gen-python-urllib3-client: clean-python-urllib3-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/spec/rest.yaml \
	  -g python \
	  -o lance_namespace_urllib3_client \
	  --additional-properties=packageName=lance_namespace_urllib3_client,packageVersion=$(VERSION),library=urllib3
	rm -rf lance_namespace_urllib3_client/.openapi-generator
	rm -f lance_namespace_urllib3_client/poetry.lock
	# Replace poetry pyproject.toml with uv-compatible version
	cp pyproject.urllib3_client.toml lance_namespace_urllib3_client/pyproject.toml

build-python-urllib3-client: gen-python-urllib3-client
	cd lance_namespace_urllib3_client; \
		uv run pytest

build-python-lance-namespace:
	cd lance_namespace; \
		uv run pytest

clean: clean-python-urllib3-client

gen: gen-python-urllib3-client

build: build-python-urllib3-client build-python-lance-namespace
