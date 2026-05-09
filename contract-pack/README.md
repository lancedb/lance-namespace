# contract-pack/

This directory holds the **shared contract artifacts** that bridge the
three Pact consumers (Java/Python/Rust) and the Spring Boot provider
verification.

## Layout

```
contract-pack/
├── README.md                  ← you are here
└── sample-pacts/              ← consumer-generated pact JSON drops here
    ├── README.md
    └── .gitkeep
```

### `sample-pacts/`

The provider verification test
[`PactProviderTest`](../java/lance-namespace-pact-tests/src/test/java/org/lance/namespace/pact/provider/PactProviderTest.java)
is annotated with:

```java
@PactFolder("../../contract-pack/sample-pacts")
```

resolved relative to its module root (`java/lance-namespace-pact-tests/`).

In CI the [pact.yml](../.github/workflows/pact.yml) workflow:

1. Runs each consumer job (`consumer-java`, `consumer-python`,
   `consumer-rust`) to **produce** their pact JSON.
2. Uploads each pact as a build artifact (`pact-files-java`, …).
3. The `provider-verify` job downloads all three artifacts back into
   `contract-pack/sample-pacts/` before invoking `mvn ... -Dtest=PactProviderTest`.

### Local reproduction

```bash
# 1. Generate consumer pacts (any subset)
( cd java   && mvn -pl lance-namespace-pact-tests -Dspotless.skip=true -Dtest=NamespaceApiPactTest test )
( cd python/lance_namespace_pact_tests && uv sync --group dev && uv run pytest tests/pact_tests -v )
( cd rust   && cargo test -p lance-namespace-pact-tests --tests )

# 2. Stage them where the provider expects to find them
cp java/lance-namespace-pact-tests/target/pacts/*.json                                contract-pack/sample-pacts/
cp python/lance_namespace_pact_tests/tests/pact_tests/pacts/*.json                    contract-pack/sample-pacts/
cp rust/lance-namespace-pact-tests/target/pacts/*.json                                contract-pack/sample-pacts/

# 3. Run provider verification
( cd java && mvn -pl lance-namespace-pact-tests -am -Dspotless.skip=true \
                 -Dtest=PactProviderTest -Dspring.profiles.active=pact test )
```

### Git tracking

The directory is kept in version control via `.gitkeep`, but the
`*.json` pact files inside `sample-pacts/` are **gitignored** — they
are build outputs regenerated on every CI run.
