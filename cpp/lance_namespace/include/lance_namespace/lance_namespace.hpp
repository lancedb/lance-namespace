/**
 * @file lance_namespace.hpp
 * @brief C++ interface for Lance Namespace core functionality.
 *
 * This header provides the C++ interface for Lance Namespace, including:
 *
 * - Namespace base class (alias for NamespaceOperations)
 * - Plugin registration (register_namespace_impl, unregister_namespace_impl)
 * - Factory function (connect)
 * - Utility functions (version_string, status_to_string)
 *
 * Example usage:
 * @code
 *   #include <lance_namespace/lance_namespace.hpp>
 *
 *   // Register a custom implementation
 *   lance_namespace::register_namespace_impl("my-impl",
 *       [](const lance_namespace::Properties &props) {
 *           return std::make_unique<MyNamespace>(props);
 *       });
 *
 *   // Connect to the namespace
 *   auto ns = lance_namespace::connect("my-impl", {{"uri", "http://localhost"}});
 *   std::cout << "ID: " << ns->namespace_id() << std::endl;
 *
 *   // Call operations (throws UnsupportedOperationError if not implemented)
 *   try {
 *       auto response = ns->list_namespaces(request);
 *   } catch (const lance_namespace::UnsupportedOperationError &e) {
 *       std::cerr << e.what() << std::endl;
 *   }
 * @endcode
 */

#ifndef LANCE_NAMESPACE_LANCE_NAMESPACE_HPP
#define LANCE_NAMESPACE_LANCE_NAMESPACE_HPP

#include "errors.hpp"
#include "lance_namespace.h"
#include "namespace_operations.hpp"

#include <functional>
#include <memory>
#include <mutex>
#include <string>
#include <unordered_map>
#include <utility>
#include <vector>

namespace lance_namespace {

/**
 * @brief A single key-value property for namespace configuration.
 */
struct Property {
    std::string key;
    std::string value;
};

/**
 * @brief A list of key-value properties passed to namespace factories.
 */
using Properties = std::vector<Property>;

/**
 * @brief Base class for namespace implementations.
 *
 * Alias for NamespaceOperations, which defines all operation methods.
 * Subclass this and implement at least namespace_id().
 */
using Namespace = NamespaceOperations;

/**
 * @brief Factory function type for creating namespace instances.
 *
 * Takes a list of properties and returns a unique_ptr to a Namespace.
 */
using Factory = std::function<std::unique_ptr<Namespace>(const Properties &)>;

namespace detail {

inline std::unordered_map<std::string, Factory> &registry() {
    static auto *registry_ = new std::unordered_map<std::string, Factory>();
    return *registry_;
}

inline std::mutex &registry_mutex() {
    static auto *mutex_ = new std::mutex();
    return *mutex_;
}

}  // namespace detail

/**
 * @brief Register a namespace implementation with a short name.
 *
 * External libraries can use this to register their implementations,
 * allowing users to use short names like "glue" instead of full class paths.
 *
 * @param name     Short name for the implementation (e.g., "glue", "hive").
 * @param factory  Factory function that creates namespace instances.
 * @throws InvalidInputError If name is empty or factory is null.
 */
inline void register_namespace_impl(std::string name, Factory factory) {
    if (name.empty()) {
        throw InvalidInputError("namespace implementation name must not be empty");
    }
    if (!factory) {
        throw InvalidInputError("namespace factory must not be empty");
    }

    std::lock_guard<std::mutex> lock(detail::registry_mutex());
    detail::registry()[std::move(name)] = std::move(factory);
}

/**
 * @brief Unregister a previously registered namespace implementation.
 *
 * @param name  The name of the implementation to remove.
 */
inline void unregister_namespace_impl(const std::string &name) {
    std::lock_guard<std::mutex> lock(detail::registry_mutex());
    detail::registry().erase(name);
}

/**
 * @brief Connect to a Lance namespace implementation.
 *
 * Creates a namespace instance from a registered name. The factory
 * registered via register_namespace_impl() is called with the provided
 * properties to construct the namespace.
 *
 * @param impl        Registered implementation name (e.g., "rest", "dir").
 * @param properties  Configuration properties passed to the factory.
 * @return A unique_ptr to the connected Namespace instance.
 * @throws NamespaceNotFoundError If the implementation is not registered.
 * @throws InternalError If the factory returns null.
 */
inline std::unique_ptr<Namespace> connect(
    const std::string &impl,
    const Properties &properties) {
    Factory factory;
    {
        std::lock_guard<std::mutex> lock(detail::registry_mutex());
        auto it = detail::registry().find(impl);
        if (it == detail::registry().end()) {
            throw NamespaceNotFoundError("unknown namespace implementation: " + impl);
        }
        factory = it->second;
    }

    auto namespace_ptr = factory(properties);
    if (!namespace_ptr) {
        throw InternalError("namespace factory returned null: " + impl);
    }
    return namespace_ptr;
}

/**
 * @brief Return the library version string.
 *
 * @return Version string (e.g., "0.8.7-beta.1").
 */
inline const char *version_string() {
    return LANCE_NAMESPACE_VERSION_STRING;
}

/**
 * @brief Convert a status code to a human-readable string.
 *
 * @param status  The status code to convert.
 * @return Static string like "ok", "not_found", "unsupported", etc.
 */
inline const char *status_to_string(lance_namespace_status_t status) {
    return lance_namespace_status_to_string(status);
}

}  // namespace lance_namespace

#endif  // LANCE_NAMESPACE_LANCE_NAMESPACE_HPP
