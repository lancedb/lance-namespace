# Lance Namespace C++ SDK

C and C++ bindings for the [Lance Namespace](https://lance.org/format/namespace) interface.

## Components

| Component | Description |
|---|---|
| `lance_namespace` | Core C API (`lance_namespace.h`) and C++20 wrapper (`lance_namespace.hpp`) |
| `lance_namespace_rest_client` | Generated C++ REST client (via OpenAPI Generator `cpp-restsdk`) |

## Requirements

| Requirement | Version |
|---|---|
| CMake | ≥ 3.22 |
| C++ standard | C++20 |
| C standard | C11 |
| Compiler (Linux) | Clang ≥ 20 (manylinux_2_28 / GLIBC ≥ 2.28) |
| Compiler (macOS) | Apple Clang, deployment target macOS 11+ |
| Compiler (Windows) | MSVC 2022 (v143) |
| cpprestsdk | ≥ 2.10 (for REST client only) |

## Build

### Linux / macOS

```bash
# Install cpprestsdk (Linux)
sudo apt install libcpprest-dev      # Debian/Ubuntu
# or build from source for manylinux — see CI workflow

# Install cpprestsdk (macOS)
brew install cpprestsdk

# Generate REST client from OpenAPI spec, then build
make build
```

### Windows (vcpkg)

```powershell
# Install cpprestsdk
vcpkg install cpprestsdk:x64-windows

cmake -S cpp -B cpp/build `
  -DCMAKE_TOOLCHAIN_FILE="<vcpkg_root>/scripts/buildsystems/vcpkg.cmake" `
  -DVCPKG_TARGET_TRIPLET=x64-windows `
  -DLANCE_NAMESPACE_BUILD_TESTS=ON
cmake --build cpp/build --config Release
```

### Build targets

| Target | Description |
|---|---|
| `make gen` | Generate REST client from OpenAPI spec |
| `make build` | Generate + build everything |
| `make test` | Build then run CTest suite |
| `make clean` | Remove generated code and build directory |

## C API usage

```c
#include <lance_namespace/lance_namespace.h>

// Connect to a REST namespace
lance_namespace_property_t props[] = {
    {"uri", "http://localhost:2333"},
};
lance_namespace_properties_t p = {props, 1};
lance_namespace_t* ns = lance_namespace_connect("rest", &p);
if (!ns) {
    fprintf(stderr, "Error %d: %s\n",
            lance_namespace_last_error_code(),
            lance_namespace_last_error_message());
    return 1;
}

printf("Connected: %s\n", lance_namespace_id(ns));
lance_namespace_destroy(ns);
```

## C++ API usage

```cpp
#include <lance_namespace/lance_namespace.hpp>

lance::namespace_::Properties props{{"uri", "http://localhost:2333"}};
auto ns = lance::namespace_::connect("rest", props);
std::cout << ns.id() << '\n';
```

## Registering a custom implementation

```c
// C
lance_namespace_t* my_factory(const lance_namespace_properties_t* props) { ... }
lance_namespace_register("my-impl", my_factory);
```

```cpp
// C++
lance::namespace_::register_impl("my-impl", my_factory);
auto ns = lance::namespace_::connect("my-impl", {{"key", "value"}});
```

## CMake integration

```cmake
find_package(LanceNamespace REQUIRED)
target_link_libraries(my_target PRIVATE LanceNamespace::lance_namespace)
```

## License

Apache 2.0 — see [LICENSE](../LICENSE).
