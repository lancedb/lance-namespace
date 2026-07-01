#!/usr/bin/env node

import { mkdir, readdir, readFile, writeFile } from "node:fs/promises";
import { join } from "node:path";
import { fileURLToPath } from "node:url";

const version = process.argv[2] ?? "0.0.0";
const scriptDir = fileURLToPath(new URL(".", import.meta.url));
const rootDir = join(scriptDir, "..");
const packageDir = join(rootDir, "lance-namespace-fetch-client");

const packageJsonPath = join(packageDir, "package.json");
const packageJson = JSON.parse(await readFile(packageJsonPath, "utf8"));
packageJson.version = version;
packageJson.repository = {
  type: "git",
  url: "https://github.com/lance-format/lance-namespace",
};
packageJson.license = "Apache-2.0";
// Publish only compiled output; consumers import from dist. Matches the
// dist-only layout of the hand-written @lance-format/lance-namespace package.
packageJson.files = ["dist"];
packageJson.scripts = {
  build: "tsc && tsc -p tsconfig.esm.json",
  lint: "tsc --noEmit && tsc -p tsconfig.esm.json --noEmit",
  test: "vitest run",
};
packageJson.devDependencies = {
  ...packageJson.devDependencies,
  typescript: "^5.8.3",
  vitest: "^2.1.9",
};

await writeFile(packageJsonPath, `${JSON.stringify(packageJson, null, 2)}\n`);

const tsconfig = {
  compilerOptions: {
    declaration: true,
    target: "es2022",
    module: "commonjs",
    moduleResolution: "node",
    outDir: "dist",
    lib: ["es2022", "dom"],
    skipLibCheck: true,
  },
  include: ["src/**/*.ts"],
  exclude: ["dist", "node_modules", "test", "vitest.config.ts"],
};

await writeFile(join(packageDir, "tsconfig.json"), `${JSON.stringify(tsconfig, null, 2)}\n`);

const tsconfigEsm = {
  extends: "./tsconfig.json",
  compilerOptions: {
    module: "esnext",
    outDir: "dist/esm",
  },
};

await writeFile(
  join(packageDir, "tsconfig.esm.json"),
  `${JSON.stringify(tsconfigEsm, null, 2)}\n`,
);

// Discover the generated API classes dynamically so new spec tags (e.g. Branch,
// MaterializedView) are exported without having to edit this script. Each
// typescript-fetch API lives in `src/apis/<Tag>Api.ts` and exports a `<Tag>Api`
// class; we re-export the classes by name (not `export *`) to avoid colliding
// the per-operation parameter interfaces with the identically named body models.
const apisDir = join(packageDir, "src", "apis");
const apiClasses = (await readdir(apisDir))
  .filter((name) => name.endsWith(".ts") && name !== "index.ts")
  .map((name) => name.slice(0, -".ts".length))
  .sort();

const apisIndex =
  `/* tslint:disable */\n/* eslint-disable */\n` +
  apiClasses.map((api) => `export { ${api} } from "./${api}";\n`).join("");
await writeFile(join(apisDir, "index.ts"), apisIndex);

const rootIndex =
  `/* tslint:disable */\n/* eslint-disable */\n` +
  `export * from "./runtime";\n` +
  `export { ${apiClasses.join(", ")} } from "./apis/index";\n` +
  `export * from "./models/index";\n`;
await writeFile(join(packageDir, "src", "index.ts"), rootIndex);

const vitestConfig = `import { defineConfig } from "vitest/config";

export default defineConfig({
  test: {
    include: ["test/**/*.test.ts"],
  },
});
`;

await writeFile(join(packageDir, "vitest.config.ts"), vitestConfig);
await mkdir(join(packageDir, "test"), { recursive: true });

const smokeTest = `import { describe, expect, it } from "vitest";

import { NamespaceApi } from "../src/apis/NamespaceApi";

describe("fetch-client smoke", () => {
  it("constructs api instance", () => {
    const api = new NamespaceApi();
    expect(api).toBeDefined();
  });
});
`;

await writeFile(join(packageDir, "test", "smoke.test.ts"), smokeTest);
