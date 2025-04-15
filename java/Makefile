
VERSION = 0.0.1

clean-java-apache-client:
	rm -rf lance-catalog-apache-client

gen-java-apache-client: clean-java-apache-client
	openapi-generator-cli generate \
	  -i ../spec/catalog.yaml \
	  -g java \
	  -o lance-catalog-apache-client \
	  --ignore-file-override=.apache-client-ignore \
	  --additional-properties=groupId=com.lancedb,artifactId=lance-catalog-apache-client,artifactVersion=$(VERSION),parentGroupId=com.lancedb,parentArtifactId=lance-catalog-root,parentVersion=$(VERSION),parentRelativePath=pom.xml,library=apache-httpclient,apiPackage=com.lancedb.lance.catalog.client.apache.api,modelPackage=com.lancedb.lance.catalog.client.apache.model,hideGenerationTimestamp=true,licenseName=Apache-2.0,licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.txt
	sed -i -e 's#<junit-version>5.10.2</junit-version>#<junit-version>5.8.2</junit-version>#g' lance-catalog-apache-client/pom.xml
	rm -f lance-catalog-apache-client/pom.xml-e
	./mvnw spotless:apply

build-java-apache-client: gen-java-apache-client
	./mvnw install -pl lance-catalog-apache-client -am

clean-java-springboot-server:
	rm -rf lance-catalog-springboot-server

gen-java-springboot-server: clean-java-springboot-server
	openapi-generator-cli generate \
	  -i ../spec/catalog.yaml \
	  -g spring \
      -o lance-catalog-springboot-server \
      --additional-properties=groupId=com.lancedb,artifactId=lance-catalog-springboot-server,artifactVersion=$(VERSION),parentGroupId=com.lancedb,parentArtifactId=lance-catalog-root,parentVersion=$(VERSION),parentRelativePath=pom.xml,library=spring-boot,interfaceOnly=true,useOptional=true,openApiNullable=false,java8=true,apiPackage=com.lancedb.lance.catalog.server.springboot.api,modelPackage=com.lancedb.lance.catalog.server.springboot.model,useTags=true,skipDefaultInterface=false,hideGenerationTimestamp=true,licenseName=Apache-2.0,licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.txt
	./mvnw spotless:apply

build-java-springboot-server: gen-java-springboot-server
	./mvnw install -pl lance-catalog-springboot-server -am

clean: clean-java-apache-client clean-java-springboot-server

gen: gen-java-apache-client gen-java-springboot-server

build: build-java-apache-client build-java-springboot-server