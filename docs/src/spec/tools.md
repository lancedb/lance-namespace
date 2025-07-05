# Tool Integration Guidelines

Tools refer to all the ML/AI training tools and analytics compute engines that can integrate with Lance tables.
The following are guidelines for tools to integrate with Lance namespaces.
Note that these are recommendations rather than hard requirements.
The goal of these guidelines is to offer a consistent user experience across different tools.

## Configuring the Implementation

We recommend tools to offer a `impl` config key that allows user to configure the Namespace implementation.
We recommend the following values for the natively supported implementations:

| Implementation        | `impl` Value |
|-----------------------|--------------|
| Directory             | dir          |
| Apache Hive MetaStore | hive         |
| REST                  | rest         |

### Configuring an Implementation Details

We recommend tools to offer implementation specific configurations using the `impl` value as the config key prefix.
For example, all config keys for the directory namespace should start with `dir.`, like `dir.path`.