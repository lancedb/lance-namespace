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

# Java model docs source and destination
JAVA_DOCS_SRC := ../java/lance-namespace-apache-client/docs
MODELS_DEST := src/operations/models

# API files to exclude (Java-specific, not data models)
API_FILES := DataApi.md IndexApi.md MetadataApi.md NamespaceApi.md TableApi.md TagApi.md TransactionApi.md

.PHONY: gen-models
gen-models:
	@echo "Copying model docs from Java generated docs..."
	@rm -rf $(MODELS_DEST)
	@mkdir -p $(MODELS_DEST)
	@for f in $(JAVA_DOCS_SRC)/*.md; do \
		filename=$$(basename "$$f"); \
		skip=false; \
		for api in $(API_FILES); do \
			if [ "$$filename" = "$$api" ]; then \
				skip=true; \
				break; \
			fi; \
		done; \
		if [ "$$skip" = "false" ]; then \
			cp "$$f" $(MODELS_DEST)/; \
		fi; \
	done
	@echo "title: Models" > $(MODELS_DEST)/.pages
	@echo "Model docs copied to $(MODELS_DEST)"

.PHONY: build
build: gen-models
	uv run mkdocs build

.PHONY: serve
serve: gen-models
	uv run mkdocs serve
