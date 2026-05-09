# lance-namespace-pact-tests (Python)

Hand-written Pact **consumer** contract tests for the Lance Namespace
Python (urllib3) client.

This package is intentionally placed *outside* the code-generated
[`lance_namespace_urllib3_client/`](../lance_namespace_urllib3_client/) so
that running `make clean` / `make gen-urllib3-client` can never wipe these
tests.

## Layout

```
lance_namespace_pact_tests/
├── pyproject.toml          # uv-managed, depends on the urllib3 client + pact-python v3
├── README.md
└── tests/
    ├── __init__.py
    └── pact_tests/
        ├── __init__.py
        ├── conftest.py            # CONSUMER_NAME / PROVIDER_NAME / PACT_DIR
        ├── error_response_dsl.py  # shared ErrorResponse matcher helper
        ├── test_namespace_api_pact.py
        └── test_table_api_pact.py
```

## Running locally

```bash
cd python/lance_namespace_pact_tests
uv sync --group dev
uv run pytest tests/pact_tests -v
```

Generated pact JSON files land in `tests/pact_tests/pacts/` (see
`PACT_DIR` in `conftest.py`). The CI workflow uploads them as artifacts
and (when `PACT_BROKER_URL` is configured) publishes them to a broker.
