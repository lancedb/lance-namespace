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

VERSION = 0.5.2
PUBLISH_FLAGS ?= --no-git-checks --access public

.PHONY: clean-fetch-client
clean-fetch-client:
	rm -rf lance-namespace-fetch-client/**

.PHONY: gen-fetch-client
gen-fetch-client: clean-fetch-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/rest.yaml \
	  -g typescript-fetch \
	  -o lance-namespace-fetch-client \
	  --additional-properties=npmName=@lance/namespace-fetch-client,npmVersion=$(VERSION),supportsES6=true,typescriptThreePlus=true,modelPropertyNaming=original,stringEnums=true
	rm -rf lance-namespace-fetch-client/.openapi-generator
	rm -f lance-namespace-fetch-client/.openapi-generator-ignore
	rm -f lance-namespace-fetch-client/git_push.sh
	node scripts/patch-fetch-client.mjs $(VERSION)

.PHONY: build-fetch-client
build-fetch-client: gen-fetch-client
	pnpm install --frozen-lockfile=false
	pnpm --filter @lance/namespace-fetch-client run build

.PHONY: clean
clean: clean-fetch-client
	rm -rf lance-namespace/dist
	rm -rf lance-namespace-rest/dist

.PHONY: gen
gen: gen-fetch-client

.PHONY: build
build: gen
	pnpm install --frozen-lockfile=false
	pnpm --filter @lance/namespace-fetch-client run build
	pnpm --filter @lance/lance-namespace run build
	pnpm --filter @lance/lance-namespace-rest run build
	pnpm --filter @lance/namespace-fetch-client run test
	pnpm --filter @lance/lance-namespace run test
	pnpm --filter @lance/lance-namespace-rest run test

.PHONY: test
test:
	pnpm install --frozen-lockfile=false
	pnpm --filter @lance/namespace-fetch-client run test
	pnpm --filter @lance/lance-namespace run test
	pnpm --filter @lance/lance-namespace-rest run test

.PHONY: lint
lint:
	pnpm install --frozen-lockfile=false
	pnpm --filter @lance/namespace-fetch-client run lint
	pnpm --filter @lance/lance-namespace run lint
	pnpm --filter @lance/lance-namespace-rest run lint

.PHONY: publish
publish:
	pnpm install --frozen-lockfile=false
	pnpm --filter @lance/namespace-fetch-client publish $(PUBLISH_FLAGS)
	pnpm --filter @lance/lance-namespace publish $(PUBLISH_FLAGS)
	pnpm --filter @lance/lance-namespace-rest publish $(PUBLISH_FLAGS)
