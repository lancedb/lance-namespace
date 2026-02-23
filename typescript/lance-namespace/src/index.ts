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

import { InvalidInputError } from "./errors";
import { LanceNamespace } from "./namespace";

export * from "@lance/namespace-fetch-client";
export * from "./errors";
export { LanceNamespace } from "./namespace";

export type NamespaceProperties = Record<string, string>;
export type NamespaceClassPath = string;
type NamespaceConstructor = new (
  properties: NamespaceProperties,
) => LanceNamespace;

export const NATIVE_IMPLS: Readonly<Record<string, NamespaceClassPath>> = {
  rest: "@lance/lance-namespace-rest#RestNamespace",
  dir: "@lance/lance-namespace-dir#DirectoryNamespace",
};

const REGISTERED_IMPLS: Map<string, NamespaceClassPath> = new Map();

export function registerNamespaceImpl(
  name: string,
  classPath: NamespaceClassPath,
): void {
  REGISTERED_IMPLS.set(name, classPath);
}

export function clearRegisteredNamespaceImplsForTest(): void {
  REGISTERED_IMPLS.clear();
}

export async function connect(
  implementation: string,
  properties: NamespaceProperties,
): Promise<LanceNamespace> {
  const classPath =
    NATIVE_IMPLS[implementation] ??
    REGISTERED_IMPLS.get(implementation) ??
    implementation;

  return loadImplementation(classPath, properties);
}

async function loadImplementation(
  classPath: NamespaceClassPath,
  properties: NamespaceProperties,
): Promise<LanceNamespace> {
  const [modulePath, exportName] = parseClassPath(classPath);

  try {
    const moduleExports: Record<string, unknown> = await import(modulePath);
    const maybeClass = moduleExports[exportName];

    if (!isNamespaceConstructor(maybeClass)) {
      throw new InvalidInputError(
        `Class ${classPath} does not implement LanceNamespace interface`,
      );
    }
    return new maybeClass(properties);
  } catch (error) {
    if (error instanceof InvalidInputError) {
      throw error;
    }
    const detail =
      error instanceof Error ? error.message : "Unknown loading failure";
    throw new InvalidInputError(
      `Failed to construct namespace impl ${classPath}: ${detail}`,
      { cause: error },
    );
  }
}

function parseClassPath(classPath: string): [string, string] {
  if (classPath.includes("#")) {
    const [modulePath, exportName] = classPath.split("#", 2);
    if (modulePath === undefined || modulePath.length === 0) {
      throw new InvalidInputError(`Invalid class path: ${classPath}`);
    }
    return [modulePath, exportName === undefined || exportName.length === 0 ? "default" : exportName];
  }

  if (
    classPath.includes(".") &&
    !classPath.includes("/") &&
    !classPath.startsWith("@")
  ) {
    const splitIndex = classPath.lastIndexOf(".");
    const modulePath = classPath.slice(0, splitIndex);
    const exportName = classPath.slice(splitIndex + 1);
    if (modulePath.length === 0 || exportName.length === 0) {
      throw new InvalidInputError(`Invalid class path: ${classPath}`);
    }
    return [modulePath, exportName];
  }

  return [classPath, "default"];
}

function isNamespaceConstructor(
  value: unknown,
): value is NamespaceConstructor {
  if (typeof value !== "function") {
    return false;
  }

  const prototype = (value as { prototype?: unknown }).prototype;
  return prototype instanceof LanceNamespace;
}
