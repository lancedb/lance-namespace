import { LanceNamespace } from "../../src/index";

export class MockNamespace extends LanceNamespace {
  public readonly properties: Record<string, string>;

  public constructor(properties: Record<string, string>) {
    super();
    this.properties = properties;
  }

  public namespaceId(): string {
    return `MockNamespace { id: '${this.properties.id ?? "mock"}' }`;
  }
}

export default MockNamespace;
