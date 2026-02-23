import { afterEach, describe, expect, it, vi } from "vitest";

import type {
  AlterTransactionRequest,
  AnalyzeTableQueryPlanRequest,
  CountTableRowsRequest,
  CreateTableRequest,
  DropNamespaceRequest,
  DescribeTableRequest,
  ExplainTableQueryPlanRequest,
  QueryTableRequest,
  UpdateTableSchemaMetadataRequest,
} from "@lance/namespace-fetch-client";

import {
  ErrorCode,
  LanceNamespace,
  TableNotFoundError,
} from "@lance/lance-namespace";

import { RestNamespace } from "../src/index";

function jsonResponse(data: unknown, status = 200): Response {
  return new Response(JSON.stringify(data), {
    status,
    headers: {
      "Content-Type": "application/json",
    },
  });
}

afterEach(() => {
  vi.unstubAllGlobals();
});

describe("rest namespace", () => {
  it("overrides all LanceNamespace operations", () => {
    const parentMethods = Object.getOwnPropertyNames(LanceNamespace.prototype).filter(
      (name) =>
        name !== "constructor" &&
        name !== "namespaceId" &&
        name !== "unsupported" &&
        typeof (LanceNamespace.prototype as Record<string, unknown>)[name] ===
          "function",
    );

    for (const method of parentMethods) {
      expect(
        Object.prototype.hasOwnProperty.call(RestNamespace.prototype, method),
      ).toBe(true);
    }
  });

  it("maps identity/context and root namespace correctly", async () => {
    const fetchMock = vi.fn(async () => jsonResponse({ namespaces: ["child"] }));
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({ uri: "http://localhost:2333" });
    const response = await ns.listNamespaces({
      id: [],
      identity: { auth_token: "token-a" },
      context: { trace_id: "trace-1" },
    });

    expect(Array.from(response.namespaces)).toEqual(["child"]);

    const [url, init] = fetchMock.mock.calls[0] as [string, RequestInit];
    expect(url).toContain("/v1/namespace/%24/list");
    expect(url).toContain("delimiter=%24");

    const headers = new Headers(init.headers);
    expect(headers.get("Authorization")).toBe("Bearer token-a");
    expect(headers.get("x-lance-ctx-trace_id")).toBe("trace-1");
  });

  it("uses configured delimiter in route serialization", async () => {
    const fetchMock = vi.fn(async () => jsonResponse({ namespaces: [] }));
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({
      uri: "http://localhost:2333",
      delimiter: ".",
    });
    await ns.listNamespaces({ id: ["a", "b"] });

    const [url] = fetchMock.mock.calls[0] as [string, RequestInit];
    expect(url).toContain("/v1/namespace/a.b/list");
    expect(url).toContain("delimiter=.");
  });

  it("gives identity/context headers higher priority than configured defaults", async () => {
    const fetchMock = vi.fn(async () => jsonResponse({ namespaces: [] }));
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({
      uri: "http://localhost:2333",
      "headers.Authorization": "Bearer static-token",
      "headers.x-lance-ctx-trace_id": "static-trace",
    });
    await ns.listNamespaces({
      id: [],
      identity: { auth_token: "dynamic-token" },
      context: { trace_id: "dynamic-trace" },
    });

    const [, init] = fetchMock.mock.calls[0] as [string, RequestInit];
    const headers = new Headers(init.headers);
    expect(headers.get("Authorization")).toBe("Bearer dynamic-token");
    expect(headers.get("x-lance-ctx-trace_id")).toBe("dynamic-trace");
  });

  it("handles arrow stream request for createTable", async () => {
    const fetchMock = vi.fn(async () =>
      jsonResponse({ location: "s3://bucket/table.lance", version: 1 }),
    );
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({ uri: "http://localhost:2333" });
    const request: CreateTableRequest = {
      id: ["ns", "tbl"],
      mode: "Overwrite",
      properties: { owner: "dev" },
      identity: { api_key: "key-1" },
    };

    const response = await ns.createTable(request, new Uint8Array([1, 2, 3]));
    expect(response.version).toBe(1);

    const [url, init] = fetchMock.mock.calls[0] as [string, RequestInit];
    expect(url).toContain("/v1/table/ns%24tbl/create");
    expect(url).toContain("mode=Overwrite");

    const headers = new Headers(init.headers);
    expect(headers.get("Content-Type")).toBe("application/vnd.apache.arrow.stream");
    expect(headers.get("x-api-key")).toBe("key-1");
    expect(headers.get("x-lance-table-properties")).toBe('{"owner":"dev"}');

    expect(init.body).toBeInstanceOf(Blob);
  });

  it("returns binary bytes for queryTable", async () => {
    const fetchMock = vi.fn(async () =>
      new Response(new Uint8Array([3, 4, 5]), {
        status: 200,
        headers: {
          "Content-Type": "application/vnd.apache.arrow.file",
        },
      }),
    );
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({ uri: "http://localhost:2333" });
    const request: QueryTableRequest = {
      id: ["ns", "tbl"],
      k: 1,
      vector: { single_vector: [0.1] },
    };

    const response = await ns.queryTable(request);
    expect([...response]).toEqual([3, 4, 5]);
  });

  it("supports plain integer and plain string responses", async () => {
    const fetchMock = vi
      .fn()
      .mockResolvedValueOnce(jsonResponse(42))
      .mockResolvedValueOnce(jsonResponse("plan"))
      .mockResolvedValueOnce(jsonResponse("analysis"));
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({ uri: "http://localhost:2333" });

    const countReq: CountTableRowsRequest = {
      id: ["ns", "tbl"],
    };
    const explainReq: ExplainTableQueryPlanRequest = {
      id: ["ns", "tbl"],
      query: {
        id: ["ns", "tbl"],
        k: 1,
        vector: { single_vector: [0.1] },
      },
    };
    const analyzeReq: AnalyzeTableQueryPlanRequest = {
      id: ["ns", "tbl"],
      k: 1,
      vector: { single_vector: [0.1] },
    };

    expect(await ns.countTableRows(countReq)).toBe(42);
    expect(await ns.explainTableQueryPlan(explainReq)).toBe("plan");
    expect(await ns.analyzeTableQueryPlan(analyzeReq)).toBe("analysis");
  });

  it("uses direct metadata map for updateTableSchemaMetadata", async () => {
    const fetchMock = vi.fn(async (_input: string, init?: RequestInit) => {
      const body = JSON.parse(String(init?.body ?? "{}"));
      expect(body).toEqual({ key: "value" });
      return jsonResponse({ key: "value" });
    });
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({ uri: "http://localhost:2333" });
    const request: UpdateTableSchemaMetadataRequest = {
      id: ["ns", "tbl"],
      metadata: { key: "value" },
    };

    const response = await ns.updateTableSchemaMetadata(request);
    expect(response.metadata).toEqual({ key: "value" });
  });

  it("maps error code to typed error", async () => {
    const fetchMock = vi.fn(async () =>
      jsonResponse({ code: 4, error: "not found" }, 404),
    );
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({ uri: "http://localhost:2333" });
    const request: DescribeTableRequest = {
      id: ["ns", "tbl"],
    };

    const promise = ns.describeTable(request);
    await expect(promise).rejects.toBeInstanceOf(TableNotFoundError);
    await expect(promise).rejects.toMatchObject({
      code: ErrorCode.TABLE_NOT_FOUND,
    });
  });

  it("only supports Restrict behavior for dropNamespace", async () => {
    const fetchMock = vi.fn(async () => jsonResponse({}));
    vi.stubGlobal("fetch", fetchMock);
    const ns = new RestNamespace({ uri: "http://localhost:2333" });

    const request: DropNamespaceRequest = {
      id: ["ns"],
      behavior: "Cascade",
    };

    await expect(ns.dropNamespace(request)).rejects.toMatchObject({
      code: ErrorCode.INVALID_INPUT,
    });
    expect(fetchMock).not.toHaveBeenCalled();
  });

  it("sends loadDetailedMetadata=false by default for describeTable", async () => {
    const fetchMock = vi.fn(async () => jsonResponse({}));
    vi.stubGlobal("fetch", fetchMock);
    const ns = new RestNamespace({ uri: "http://localhost:2333" });
    await ns.describeTable({ id: ["ns", "tbl"] });

    const [url] = fetchMock.mock.calls[0] as [string, RequestInit];
    expect(url).toContain("load_detailed_metadata=false");
  });

  it("supports extra metadata operations", async () => {
    const fetchMock = vi
      .fn()
      .mockResolvedValueOnce(jsonResponse({ id: ["ns", "tbl"], versions: [] }))
      .mockResolvedValueOnce(jsonResponse({ id: ["ns", "txn"] }));
    vi.stubGlobal("fetch", fetchMock);

    const ns = new RestNamespace({ uri: "http://localhost:2333" });
    await ns.listTableVersions({ id: ["ns", "tbl"], descending: true });

    const alterRequest: AlterTransactionRequest = {
      id: ["ns", "txn"],
      actions: [{ setStatusAction: { status: "Canceled" } }],
    };
    await ns.alterTransaction(alterRequest);

    expect(fetchMock).toHaveBeenCalledTimes(2);
  });
});
