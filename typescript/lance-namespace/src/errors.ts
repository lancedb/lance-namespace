// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import type { ErrorResponse } from "@lance/namespace-fetch-client";

export enum ErrorCode {
  UNSUPPORTED = 0,
  NAMESPACE_NOT_FOUND = 1,
  NAMESPACE_ALREADY_EXISTS = 2,
  NAMESPACE_NOT_EMPTY = 3,
  TABLE_NOT_FOUND = 4,
  TABLE_ALREADY_EXISTS = 5,
  TABLE_INDEX_NOT_FOUND = 6,
  TABLE_INDEX_ALREADY_EXISTS = 7,
  TABLE_TAG_NOT_FOUND = 8,
  TABLE_TAG_ALREADY_EXISTS = 9,
  TRANSACTION_NOT_FOUND = 10,
  TABLE_VERSION_NOT_FOUND = 11,
  TABLE_COLUMN_NOT_FOUND = 12,
  INVALID_INPUT = 13,
  CONCURRENT_MODIFICATION = 14,
  PERMISSION_DENIED = 15,
  UNAUTHENTICATED = 16,
  SERVICE_UNAVAILABLE = 17,
  INTERNAL = 18,
  INVALID_TABLE_STATE = 19,
  TABLE_SCHEMA_VALIDATION_ERROR = 20,
  THROTTLING = 21,
}

export interface LanceNamespaceErrorOptions {
  cause?: unknown;
  status?: number;
  detail?: string;
  instance?: string;
}

export class LanceNamespaceError extends Error {
  public readonly code: ErrorCode;
  public readonly status?: number;
  public readonly detail?: string;
  public readonly instance?: string;

  public constructor(
    code: ErrorCode,
    message: string,
    options: LanceNamespaceErrorOptions = {},
  ) {
    super(message);
    this.name = this.constructor.name;
    this.code = code;
    this.status = options.status;
    this.detail = options.detail;
    this.instance = options.instance;
    if (options.cause !== undefined) {
      (this as Error & { cause?: unknown }).cause = options.cause;
    }
  }
}

export class UnsupportedOperationError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.UNSUPPORTED, message, options);
  }
}

export class NamespaceNotFoundError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.NAMESPACE_NOT_FOUND, message, options);
  }
}

export class NamespaceAlreadyExistsError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.NAMESPACE_ALREADY_EXISTS, message, options);
  }
}

export class NamespaceNotEmptyError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.NAMESPACE_NOT_EMPTY, message, options);
  }
}

export class TableNotFoundError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_NOT_FOUND, message, options);
  }
}

export class TableAlreadyExistsError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_ALREADY_EXISTS, message, options);
  }
}

export class TableIndexNotFoundError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_INDEX_NOT_FOUND, message, options);
  }
}

export class TableIndexAlreadyExistsError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_INDEX_ALREADY_EXISTS, message, options);
  }
}

export class TableTagNotFoundError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_TAG_NOT_FOUND, message, options);
  }
}

export class TableTagAlreadyExistsError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_TAG_ALREADY_EXISTS, message, options);
  }
}

export class TransactionNotFoundError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TRANSACTION_NOT_FOUND, message, options);
  }
}

export class TableVersionNotFoundError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_VERSION_NOT_FOUND, message, options);
  }
}

export class TableColumnNotFoundError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_COLUMN_NOT_FOUND, message, options);
  }
}

export class InvalidInputError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.INVALID_INPUT, message, options);
  }
}

export class ConcurrentModificationError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.CONCURRENT_MODIFICATION, message, options);
  }
}

export class PermissionDeniedError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.PERMISSION_DENIED, message, options);
  }
}

export class UnauthenticatedError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.UNAUTHENTICATED, message, options);
  }
}

export class ServiceUnavailableError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.SERVICE_UNAVAILABLE, message, options);
  }
}

export class InternalError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.INTERNAL, message, options);
  }
}

export class InvalidTableStateError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.INVALID_TABLE_STATE, message, options);
  }
}

export class TableSchemaValidationError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.TABLE_SCHEMA_VALIDATION_ERROR, message, options);
  }
}

export class ThrottlingError extends LanceNamespaceError {
  public constructor(message: string, options: LanceNamespaceErrorOptions = {}) {
    super(ErrorCode.THROTTLING, message, options);
  }
}

const ERROR_CODE_TO_CONSTRUCTOR: Record<
  number,
  (message: string, options?: LanceNamespaceErrorOptions) => LanceNamespaceError
> = {
  [ErrorCode.UNSUPPORTED]: (message, options) =>
    new UnsupportedOperationError(message, options),
  [ErrorCode.NAMESPACE_NOT_FOUND]: (message, options) =>
    new NamespaceNotFoundError(message, options),
  [ErrorCode.NAMESPACE_ALREADY_EXISTS]: (message, options) =>
    new NamespaceAlreadyExistsError(message, options),
  [ErrorCode.NAMESPACE_NOT_EMPTY]: (message, options) =>
    new NamespaceNotEmptyError(message, options),
  [ErrorCode.TABLE_NOT_FOUND]: (message, options) =>
    new TableNotFoundError(message, options),
  [ErrorCode.TABLE_ALREADY_EXISTS]: (message, options) =>
    new TableAlreadyExistsError(message, options),
  [ErrorCode.TABLE_INDEX_NOT_FOUND]: (message, options) =>
    new TableIndexNotFoundError(message, options),
  [ErrorCode.TABLE_INDEX_ALREADY_EXISTS]: (message, options) =>
    new TableIndexAlreadyExistsError(message, options),
  [ErrorCode.TABLE_TAG_NOT_FOUND]: (message, options) =>
    new TableTagNotFoundError(message, options),
  [ErrorCode.TABLE_TAG_ALREADY_EXISTS]: (message, options) =>
    new TableTagAlreadyExistsError(message, options),
  [ErrorCode.TRANSACTION_NOT_FOUND]: (message, options) =>
    new TransactionNotFoundError(message, options),
  [ErrorCode.TABLE_VERSION_NOT_FOUND]: (message, options) =>
    new TableVersionNotFoundError(message, options),
  [ErrorCode.TABLE_COLUMN_NOT_FOUND]: (message, options) =>
    new TableColumnNotFoundError(message, options),
  [ErrorCode.INVALID_INPUT]: (message, options) =>
    new InvalidInputError(message, options),
  [ErrorCode.CONCURRENT_MODIFICATION]: (message, options) =>
    new ConcurrentModificationError(message, options),
  [ErrorCode.PERMISSION_DENIED]: (message, options) =>
    new PermissionDeniedError(message, options),
  [ErrorCode.UNAUTHENTICATED]: (message, options) =>
    new UnauthenticatedError(message, options),
  [ErrorCode.SERVICE_UNAVAILABLE]: (message, options) =>
    new ServiceUnavailableError(message, options),
  [ErrorCode.INTERNAL]: (message, options) => new InternalError(message, options),
  [ErrorCode.INVALID_TABLE_STATE]: (message, options) =>
    new InvalidTableStateError(message, options),
  [ErrorCode.TABLE_SCHEMA_VALIDATION_ERROR]: (message, options) =>
    new TableSchemaValidationError(message, options),
  [ErrorCode.THROTTLING]: (message, options) =>
    new ThrottlingError(message, options),
};

export function fromErrorCode(
  code: number,
  message: string,
  options: LanceNamespaceErrorOptions = {},
): LanceNamespaceError {
  const ctor = ERROR_CODE_TO_CONSTRUCTOR[code];
  if (ctor !== undefined) {
    return ctor(message, options);
  }
  return new InternalError(message, options);
}

export function fromErrorResponse(
  response: Partial<ErrorResponse> & { code?: number },
  fallbackMessage: string,
  options: LanceNamespaceErrorOptions = {},
): LanceNamespaceError {
  const message = response.error ?? response.detail ?? fallbackMessage;
  const merged: LanceNamespaceErrorOptions = {
    ...options,
    detail: response.detail ?? options.detail,
    instance: response.instance ?? options.instance,
  };
  if (typeof response.code === "number") {
    return fromErrorCode(response.code, message, merged);
  }
  return new InternalError(message, merged);
}
