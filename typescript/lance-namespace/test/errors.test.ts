import { describe, expect, it } from "vitest";

import {
  ErrorCode,
  InternalError,
  TableNotFoundError,
  fromErrorCode,
  fromErrorResponse,
} from "../src/errors";

describe("errors", () => {
  it("maps known code to typed error", () => {
    const err = fromErrorCode(4, "missing");
    expect(err).toBeInstanceOf(TableNotFoundError);
    expect(err.code).toBe(ErrorCode.TABLE_NOT_FOUND);
  });

  it("maps unknown code to internal error", () => {
    const err = fromErrorCode(999, "unknown");
    expect(err).toBeInstanceOf(InternalError);
    expect(err.code).toBe(ErrorCode.INTERNAL);
  });

  it("builds from error response", () => {
    const err = fromErrorResponse(
      {
        code: 4,
        error: "table not found",
        detail: "detail",
        instance: "req-1",
      },
      "fallback",
    );

    expect(err).toBeInstanceOf(TableNotFoundError);
    expect(err.message).toBe("table not found");
    expect(err.detail).toBe("detail");
    expect(err.instance).toBe("req-1");
  });
});
