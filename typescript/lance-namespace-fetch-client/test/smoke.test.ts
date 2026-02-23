import { describe, expect, it } from "vitest";

import { NamespaceApi } from "../src/apis/NamespaceApi";

describe("fetch-client smoke", () => {
  it("constructs api instance", () => {
    const api = new NamespaceApi();
    expect(api).toBeDefined();
  });
});
