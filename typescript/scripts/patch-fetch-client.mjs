#!/usr/bin/env node

import { mkdir, readFile, writeFile } from "node:fs/promises";
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

const apisIndex = `/* tslint:disable */\n/* eslint-disable */\nexport { DataApi } from "./DataApi";\nexport { IndexApi } from "./IndexApi";\nexport { MetadataApi } from "./MetadataApi";\nexport { NamespaceApi } from "./NamespaceApi";\nexport { TableApi } from "./TableApi";\nexport { TagApi } from "./TagApi";\nexport { TransactionApi } from "./TransactionApi";\n`;
await writeFile(join(packageDir, "src", "apis", "index.ts"), apisIndex);

const rootIndex = `/* tslint:disable */\n/* eslint-disable */\nexport * from "./runtime";\nexport { DataApi, IndexApi, MetadataApi, NamespaceApi, TableApi, TagApi, TransactionApi } from "./apis/index";\nexport * from "./models/index";\n`;
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
