import { describe, expect, it } from "vitest";

import type { ListNamespacesRequest } from "@lance/namespace-fetch-client";

import {
  LanceNamespace,
  NATIVE_IMPLS,
  UnsupportedOperationError,
  clearRegisteredNamespaceImplsForTest,
  connect,
  registerNamespaceImpl,
} from "../src/index";

class MockNamespace extends LanceNamespace {
  public readonly properties: Record<string, string>;

  public constructor(properties: Record<string, string>) {
    super();
    this.properties = properties;
  }

  public namespaceId(): string {
    return `MockNamespace { id: '${this.properties.id ?? "mock"}' }`;
  }
}

describe("namespace interface and registry", () => {
  it("default methods throw unsupported", async () => {
    const ns = new MockNamespace({});

    await expect(
      ns.listNamespaces({ id: [] } as ListNamespacesRequest),
    ).rejects.toBeInstanceOf(UnsupportedOperationError);
  });

  it("connects with class path", async () => {
    const ns = await connect("../test/fixtures/mock_namespace#MockNamespace", {
      id: "class-path",
    });
    expect(ns.namespaceId()).toContain("class-path");
  });

  it("connects with registered alias", async () => {
    clearRegisteredNamespaceImplsForTest();
    registerNamespaceImpl("mock", "../test/fixtures/mock_namespace#default");

    const ns = await connect("mock", { id: "registered" });
    expect(ns.namespaceId()).toContain("registered");
  });

  it("rejects invalid class path", async () => {
    await expect(connect("non-existent-module", {})).rejects.toThrow(
      "Failed to construct namespace impl",
    );
  });

  it("defines python-style native aliases", () => {
    expect(NATIVE_IMPLS.rest).toBe("@lance/lance-namespace-rest#RestNamespace");
    expect(NATIVE_IMPLS.dir).toBe("@lance/lance-namespace-dir#DirectoryNamespace");
  });
});
