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

.PHONY: lint
lint:
	uv run openapi-spec-validator --errors all docs/src/rest.yaml

.PHONY: clean-rust
clean-rust:
	cd rust; make clean

.PHONY: gen-rust
gen-rust:
	cd rust; make gen

.PHONY: build-rust
build-rust:
	cd rust; make build

.PHONY: clean-python
clean-python:
	cd python; make clean

.PHONY: gen-python
gen-python:
	cd python; make gen

.PHONY: build-python
build-python:
	cd python; make build

.PHONY: clean-java
clean-java:
	cd java; make clean

.PHONY: gen-java
gen-java:
	cd java; make gen

.PHONY: build-java
build-java:
	cd java; make build

.PHONY: gen-docs
gen-docs:
	cd docs; make gen

.PHONY: build-docs
build-docs:
	cd docs; make build

.PHONY: serve-docs
serve-docs:
	cd docs; make serve

.PHONY: clean
clean: clean-rust clean-python clean-java

.PHONY: gen
gen: lint gen-docs gen-rust gen-python gen-java

.PHONY: build
build: lint build-docs build-rust build-python build-java
