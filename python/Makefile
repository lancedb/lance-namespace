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

.PHONY: clean-urllib3-client
clean-urllib3-client:
	rm -rf lance_namespace_urllib3_client/**
	mkdir -p lance_namespace_urllib3_client
	cp pyproject.urllib3_client.toml lance_namespace_urllib3_client/pyproject.toml

.PHONY: gen-urllib3-client
gen-urllib3-client: clean-urllib3-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/rest.yaml \
	  -g python \
	  -o lance_namespace_urllib3_client \
	  --additional-properties=packageName=lance_namespace_urllib3_client,packageVersion=$(VERSION),library=urllib3
	rm -rf lance_namespace_urllib3_client/.openapi-generator
	rm -f lance_namespace_urllib3_client/poetry.lock
	# Replace poetry pyproject.toml with uv-compatible version
	cp pyproject.urllib3_client.toml lance_namespace_urllib3_client/pyproject.toml

.PHONY: build-urllib3-client
build-urllib3-client: gen-urllib3-client
	cd lance_namespace_urllib3_client; \
		uv run pytest

.PHONY: build-ns
build-ns: build-urllib3-client
	cd lance_namespace; \
		cp ../../README.md README.md; \
		uv pip install ".[glue,hive2,test]"; \
		uv run pytest

.PHONY: publish-ns
publish-ns:
	cd lance_namespace; \
		uv build

.PHONY: publish-urllib3-client
publish-urllib3-client:
	cd lance_namespace_urllib3_client; \
		uv build

.PHONY: clean
clean: clean-urllib3-client

.PHONY: gen
gen: gen-urllib3-client

.PHONY: build
build: build-urllib3-client build-ns

.PHONY: publish
publish: publish-urllib3-client publish-ns