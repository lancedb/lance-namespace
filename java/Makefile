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

VERSION = 0.0.3

.PHONY: clean-java-apache-client
clean-java-apache-client:
	rm -rf lance-namespace-apache-client

.PHONY: gen-java-apache-client
gen-java-apache-client: clean-java-apache-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/spec/rest.yaml \
	  -g java \
	  -o lance-namespace-apache-client \
	  --ignore-file-override=.apache-client-ignore \
	  --type-mappings=file=byte[] \
	  --additional-properties=groupId=com.lancedb,artifactId=lance-namespace-apache-client,artifactVersion=$(VERSION),parentGroupId=com.lancedb,parentArtifactId=lance-namespace-root,parentVersion=$(VERSION),parentRelativePath=pom.xml,library=apache-httpclient,apiPackage=com.lancedb.lance.namespace.client.apache.api,modelPackage=com.lancedb.lance.namespace.model,hideGenerationTimestamp=true,licenseName=Apache-2.0,licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.txt
	sed -i -e 's#<junit-version>5.10.2</junit-version>#<junit-version>5.8.2</junit-version>#g' lance-namespace-apache-client/pom.xml
	rm -f lance-namespace-apache-client/pom.xml-e
	rm -rf lance-namespace-apache-client/.openapi-generator-ignore
	rm -rf lance-namespace-apache-client/.openapi-generator

.PHONY: lint-java-apache-client
lint-java-apache-client: gen-java-apache-client gen-java-springboot-server
	./mvnw spotless:apply -pl lance-namespace-apache-client -am

.PHONY: build-java-apache-client
build-java-apache-client: gen-java-apache-client lint-java-apache-client
	./mvnw install -pl lance-namespace-apache-client -am

.PHONY: clean-java-springboot-server
clean-java-springboot-server:
	rm -rf lance-namespace-springboot-server

.PHONY: gen-java-springboot-server
gen-java-springboot-server: clean-java-springboot-server
	uv run openapi-generator-cli generate \
	  -i ../docs/src/spec/rest.yaml \
	  -g spring \
      -o lance-namespace-springboot-server \
      --additional-properties=groupId=com.lancedb,artifactId=lance-namespace-springboot-server,artifactVersion=$(VERSION),parentGroupId=com.lancedb,parentArtifactId=lance-namespace-root,parentVersion=$(VERSION),parentRelativePath=pom.xml,library=spring-boot,interfaceOnly=true,useOptional=true,openApiNullable=false,java8=true,apiPackage=com.lancedb.lance.namespace.server.springboot.api,modelPackage=com.lancedb.lance.namespace.server.springboot.model,useTags=true,skipDefaultInterface=false,hideGenerationTimestamp=true,licenseName=Apache-2.0,licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.txt
	rm -rf lance-namespace-springboot-server/.openapi-generator-ignore
	rm -rf lance-namespace-springboot-server/.openapi-generator

.PHONY: lint-java-springboot-server
lint-java-springboot-server: gen-java-apache-client gen-java-springboot-server
	./mvnw spotless:apply -pl lance-namespace-springboot-server -am

.PHONY: build-java-springboot-server
build-java-springboot-server: gen-java-springboot-server lint-java-springboot-server
	./mvnw install -pl lance-namespace-springboot-server -am

.PHONY: build-java-core
build-java-core:
	./mvnw spotless:apply -pl lance-namespace-core -am
	./mvnw install -pl lance-namespace-core -am

.PHONY: build-java-adapter
build-java-adapter:
	./mvnw spotless:apply -pl lance-namespace-adapter -am
	./mvnw install -pl lance-namespace-adapter -am

.PHONY: clean
clean: clean-java-apache-client clean-java-springboot-server

.PHONY: gen
gen: gen-java-apache-client gen-java-springboot-server lint-java-apache-client lint-java-springboot-server

.PHONY: build
build: build-java-apache-client build-java-springboot-server build-java-core build-java-adapter