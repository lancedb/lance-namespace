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

VERSION = 0.0.8

.PHONY: clean-apache-client
clean-apache-client:
	rm -rf lance-namespace-apache-client

.PHONY: gen-apache-client
gen-apache-client: clean-apache-client
	uv run openapi-generator-cli generate \
	  -i ../docs/src/rest.yaml \
	  -g java \
	  -o lance-namespace-apache-client \
	  --ignore-file-override=.apache-client-ignore \
	  --type-mappings=file=byte[] \
	  --additional-properties=groupId=com.lancedb,artifactId=lance-namespace-apache-client,artifactVersion=$(VERSION),parentGroupId=com.lancedb,parentArtifactId=lance-namespace-root,parentVersion=$(VERSION),parentRelativePath=pom.xml,library=apache-httpclient,apiPackage=com.lancedb.lance.namespace.client.apache.api,modelPackage=com.lancedb.lance.namespace.model,hideGenerationTimestamp=true,licenseName=Apache-2.0,licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.txt
	sed -i -e 's#<junit-version>5.10.2</junit-version>#<junit-version>5.8.2</junit-version>#g' lance-namespace-apache-client/pom.xml
	rm -f lance-namespace-apache-client/pom.xml-e
	rm -rf lance-namespace-apache-client/.openapi-generator-ignore
	rm -rf lance-namespace-apache-client/.openapi-generator

.PHONY: lint-apache-client
lint-apache-client: gen-apache-client gen-springboot-server
	./mvnw spotless:apply -pl lance-namespace-apache-client -am

.PHONY: build-apache-client
build-apache-client: gen-apache-client lint-apache-client
	./mvnw install -pl lance-namespace-apache-client -am

.PHONY: clean-springboot-server
clean-springboot-server:
	rm -rf lance-namespace-springboot-server

.PHONY: gen-springboot-server
gen-springboot-server: clean-springboot-server
	uv run openapi-generator-cli generate \
	  -i ../docs/src/rest.yaml \
	  -g spring \
      -o lance-namespace-springboot-server \
      --additional-properties=groupId=com.lancedb,artifactId=lance-namespace-springboot-server,artifactVersion=$(VERSION),parentGroupId=com.lancedb,parentArtifactId=lance-namespace-root,parentVersion=$(VERSION),parentRelativePath=pom.xml,library=spring-boot,interfaceOnly=true,useOptional=true,openApiNullable=false,java8=true,apiPackage=com.lancedb.lance.namespace.server.springboot.api,modelPackage=com.lancedb.lance.namespace.server.springboot.model,useTags=true,skipDefaultInterface=false,hideGenerationTimestamp=true,licenseName=Apache-2.0,licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.txt,useSpringBoot3=true
	rm -rf lance-namespace-springboot-server/.openapi-generator-ignore
	rm -rf lance-namespace-springboot-server/.openapi-generator
	rm -rf lance-namespace-springboot-server/pom.xml
	cp springboot-server-pom.xml lance-namespace-springboot-server/pom.xml

.PHONY: lint-springboot-server
lint-springboot-server: gen-apache-client gen-springboot-server
	./mvnw spotless:apply -pl lance-namespace-springboot-server -am

.PHONY: build-springboot-server
build-springboot-server: gen-springboot-server lint-springboot-server
	./mvnw install -pl lance-namespace-springboot-server -am

.PHONY: build-core
build-core:
	./mvnw spotless:apply -pl lance-namespace-core -am
	./mvnw install -pl lance-namespace-core -am

.PHONY: build-adapter
build-adapter:
	./mvnw spotless:apply -pl lance-namespace-adapter -am
	./mvnw install -pl lance-namespace-adapter -am

.PHONY: build-hive2
build-hive2:
	./mvnw spotless:apply -pl lance-namespace-hive2 -am
	./mvnw install -pl lance-namespace-hive2 -am

.PHONY: build-hive3
build-hive3:
	./mvnw spotless:apply -pl lance-namespace-hive3 -am
	./mvnw install -pl lance-namespace-hive3 -am

.PHONY: build-glue
build-glue:
	./mvnw spotless:apply -pl lance-namespace-glue -am
	./mvnw install -pl lance-namespace-glue -am

.PHONY: build-unity
build-unity:
	./mvnw spotless:apply -pl lance-namespace-unity -am
	./mvnw install -pl lance-namespace-unity -am

.PHONY: build-lancedb
build-lancedb:
	./mvnw spotless:apply -pl lance-namespace-lancedb -am
	./mvnw install -pl lance-namespace-lancedb -am

.PHONY: clean
clean: clean-apache-client clean-springboot-server

.PHONY: gen
gen: gen-apache-client gen-springboot-server lint-apache-client lint-springboot-server

.PHONY: build
build: build-apache-client build-springboot-server build-core build-adapter build-hive2 build-hive3 build-glue build-unity build-lancedb