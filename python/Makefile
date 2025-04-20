
VERSION = 0.0.1

clean-python-urllib3-client:
	rm -rf lance-catalog-urllib3-client/**

gen-python-urllib3-client: clean-python-urllib3-client
	openapi-generator-cli generate \
	  -i ../spec/catalog.yaml \
	  -g python \
	  -o lance_catalog_urllib3_client \
	  --additional-properties=packageName=lance_catalog_urllib3_client,packageVersion=$(VERSION),library=urllib3
  	# TODO: using .openapi-generator-ignore was not working, manually delete unused files for now
	rm -rf lance_catalog_urllib3_client/.github
	rm -rf lance_catalog_urllib3_client/.gitignore
	rm -rf lance_catalog_urllib3_client/.gitlab-ci.yml
	rm -rf lance_catalog_urllib3_client/.travis.yml
	rm -rf lance_catalog_urllib3_client/git_push.sh
	rm -rf lance_catalog_urllib3_client/requirements.txt
	rm -rf lance_catalog_urllib3_client/setup.cfg
	rm -rf lance_catalog_urllib3_client/setup.py
	rm -rf lance_catalog_urllib3_client/test-requirements.txt
	rm -rf lance_catalog_urllib3_client/tox.ini

build-python-urllib3-client: gen-python-urllib3-client
	cd lance_catalog_urllib3_client; \
		poetry run pytest

clean: clean-python-urllib3-client

gen: gen-python-urllib3-client

build: build-python-urllib3-client
