
VERSION = 0.0.1

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

clean: clean-rust clean-python clean-java

gen: gen-rust gen-python gen-java

build: build-rust build-python build-java