# sample-pacts/

Drop zone for **consumer-generated Pact JSON** files. The Spring Boot
provider verification test
([`PactProviderTest`](../../java/lance-namespace-pact-tests/src/test/java/org/lance/namespace/pact/provider/PactProviderTest.java))
loads every `*.json` under this directory via
`@PactFolder("../../contract-pack/sample-pacts")`.

Expected files (one per consumer):

| File | Producer |
| --- | --- |
| `lance-namespace-java-apache-lance-namespace-server.json`     | `mvn -pl lance-namespace-pact-tests -Dtest=NamespaceApiPactTest test` |
| `lance-namespace-python-urllib3-lance-namespace-server.json`  | `uv run pytest python/lance_namespace_pact_tests/tests/pact_tests` |
| `lance-namespace-rust-reqwest-lance-namespace-server.json`    | `cargo test -p lance-namespace-pact-tests --tests`                    |

These JSON files are **build artifacts**: they are regenerated on every
CI run and are intentionally **gitignored**. Only `.gitkeep` and this
README are tracked.

See [../README.md](../README.md) for the full local reproduction
workflow.
