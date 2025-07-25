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

lint:
	uv run openapi-spec-validator --errors all docs/src/spec/rest.yaml

clean-rust:
	cd rust; make clean

gen-rust:
	cd rust; make gen

build-rust:
	cd rust; make build

clean-python:
	cd python; make clean

gen-python:
	cd python; make gen

build-python:
	cd python; make build

clean-java:
	cd java; make clean

gen-java:
	cd java; make gen

build-java:
	cd java; make build

gen-docs:
	cd docs/src/spec; \
		uv run update_line_numbers.py

clean: clean-rust clean-python clean-java

gen: lint gen-docs gen-rust gen-python gen-java

build: lint gen-docs build-rust build-python build-java
