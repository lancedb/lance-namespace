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
	rm -rf lance-namespace-urllib3-client/**

gen-python-urllib3-client: clean-python-urllib3-client
	openapi-generator-cli generate \
	  -i ../spec/rest.yaml \
	  -g python \
	  -o lance_namespace_urllib3_client \
	  --additional-properties=packageName=lance_namespace_urllib3_client,packageVersion=$(VERSION),library=urllib3
  	# TODO: using .openapi-generator-ignore was not working, manually delete unused files for now
	rm -rf lance_namespace_urllib3_client/.github
	rm -rf lance_namespace_urllib3_client/.gitignore
	rm -rf lance_namespace_urllib3_client/.gitlab-ci.yml
	rm -rf lance_namespace_urllib3_client/.travis.yml
	rm -rf lance_namespace_urllib3_client/git_push.sh
	rm -rf lance_namespace_urllib3_client/requirements.txt
	rm -rf lance_namespace_urllib3_client/setup.cfg
	rm -rf lance_namespace_urllib3_client/setup.py
	rm -rf lance_namespace_urllib3_client/test-requirements.txt
	rm -rf lance_namespace_urllib3_client/tox.ini

build-python-urllib3-client: gen-python-urllib3-client
	cd lance_namespace_urllib3_client; \
		poetry install; \
		poetry run pytest

clean: clean-python-urllib3-client

gen: gen-python-urllib3-client

build: build-python-urllib3-client
