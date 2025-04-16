
VERSION = 0.0.1

clean-rust-reqwest-client:
	rm -rf lance-catalog-reqwest-client/**

gen-rust-reqwest-client: clean-rust-reqwest-client
	openapi-generator-cli generate \
	  -i ../spec/catalog.yaml \
	  -g rust \
	  -o lance-catalog-reqwest-client \
	  --additional-properties=packageName=lance-catalog-reqwest-client,packageVersion=$(VERSION),asyncApi=true,library=reqwest

build-rust-reqwest-client: gen-rust-reqwest-client
	cargo test

clean: clean-rust-reqwest-client

gen: gen-rust-reqwest-client

build: build-rust-reqwest-client