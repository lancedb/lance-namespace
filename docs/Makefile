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
	uv run mkdocs-linkcheck src

.PHONY: gen
gen: lint
	cd src/spec; uv run update_line_numbers.py

.PHONY: build
build: gen
	uv run mkdocs build

.PHONY: serve
serve: gen
	uv run mkdocs serve

.PHONY: deploy
deploy:
	uv run mkdocs gh-deploy --force
