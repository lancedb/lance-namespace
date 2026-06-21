#include "lance_namespace/lance_namespace.h"

#include "lance_namespace/errors.h"
#include "lance_namespace/models.h"

#include <cstdlib>
#include <cstring>
#include <mutex>
#include <string>
#include <unordered_map>

namespace {

struct RegistryEntry {
    lance_namespace_create_fn create_fn;
};

std::unordered_map<std::string, RegistryEntry> &registry() {
    static auto *registry_ = new std::unordered_map<std::string, RegistryEntry>();
    return *registry_;
}

std::mutex &registry_mutex() {
    static auto *mutex_ = new std::mutex();
    return *mutex_;
}

}  // namespace

struct lance_namespace_handle {
    const lance_namespace_vtable_t *vtable;
    void *instance;
};

namespace {

void set_error(
    lance_namespace_error_t *error,
    lance_namespace_status_t code,
    const std::string &message) {
    if (error == nullptr) {
        return;
    }

    std::free(error->message);
    error->message = nullptr;
    error->code = code;
    error->message = static_cast<char *>(std::malloc(message.size() + 1));
    if (error->message == nullptr) {
        return;
    }
    std::memcpy(error->message, message.c_str(), message.size() + 1);
}

}  // namespace

extern "C" const char *lance_namespace_version_string(void) {
    return LANCE_NAMESPACE_VERSION_STRING;
}

extern "C" const char *lance_namespace_status_to_string(
    lance_namespace_status_t status) {
    switch (status) {
        case LANCE_NAMESPACE_STATUS_OK:
            return "ok";
        case LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT:
            return "invalid_argument";
        case LANCE_NAMESPACE_STATUS_NOT_FOUND:
            return "not_found";
        case LANCE_NAMESPACE_STATUS_ALREADY_EXISTS:
            return "already_exists";
        case LANCE_NAMESPACE_STATUS_UNSUPPORTED:
            return "unsupported";
        case LANCE_NAMESPACE_STATUS_INTERNAL:
        default:
            return "internal";
    }
}

extern "C" const char *lance_namespace_option_get(
    const lance_namespace_options_t *options,
    const char *key) {
    if (options == nullptr || key == nullptr || options->properties == nullptr) {
        return nullptr;
    }

    for (size_t i = 0; i < options->property_count; ++i) {
        const auto &property = options->properties[i];
        if (property.key != nullptr && std::strcmp(property.key, key) == 0) {
            return property.value;
        }
    }
    return nullptr;
}

extern "C" const char *lance_namespace_header_get(
    const lance_namespace_headers_t *headers,
    const char *name) {
    if (headers == nullptr || name == nullptr || headers->items == nullptr) {
        return nullptr;
    }

    for (size_t i = 0; i < headers->count; ++i) {
        const auto &header = headers->items[i];
        if (header.name != nullptr && std::strcmp(header.name, name) == 0) {
            return header.value;
        }
    }
    return nullptr;
}

extern "C" lance_namespace_handle_t *lance_namespace_handle_create(
    const lance_namespace_vtable_t *vtable,
    void *instance) {
    if (vtable == nullptr) {
        return nullptr;
    }

    auto *handle = new lance_namespace_handle{vtable, instance};
    return handle;
}

extern "C" void *lance_namespace_handle_instance(
    const lance_namespace_handle_t *handle) {
    if (handle == nullptr) {
        return nullptr;
    }
    return handle->instance;
}

extern "C" void lance_namespace_handle_release(lance_namespace_handle_t *handle) {
    if (handle == nullptr) {
        return;
    }

    if (handle->vtable != nullptr && handle->vtable->destroy != nullptr) {
        handle->vtable->destroy(handle->instance);
    }
    delete handle;
}

extern "C" const char *lance_namespace_namespace_id(
    const lance_namespace_handle_t *handle) {
    if (handle == nullptr || handle->vtable == nullptr ||
        handle->vtable->namespace_id == nullptr) {
        return nullptr;
    }
    return handle->vtable->namespace_id(handle);
}

extern "C" lance_namespace_status_t lance_namespace_register_impl(
    const char *name,
    lance_namespace_create_fn create_fn) {
    if (name == nullptr || name[0] == '\0' || create_fn == nullptr) {
        return LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT;
    }

    std::lock_guard<std::mutex> lock(registry_mutex());
    registry()[name] = RegistryEntry{create_fn};
    return LANCE_NAMESPACE_STATUS_OK;
}

extern "C" lance_namespace_status_t lance_namespace_unregister_impl(
    const char *name) {
    if (name == nullptr || name[0] == '\0') {
        return LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT;
    }

    std::lock_guard<std::mutex> lock(registry_mutex());
    auto erased = registry().erase(name);
    return erased == 0 ? LANCE_NAMESPACE_STATUS_NOT_FOUND : LANCE_NAMESPACE_STATUS_OK;
}

extern "C" lance_namespace_status_t lance_namespace_connect(
    const char *impl,
    const lance_namespace_options_t *options,
    lance_namespace_handle_t **out_namespace,
    lance_namespace_error_t *error) {
    if (out_namespace == nullptr) {
        set_error(error, LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT, "out_namespace must not be null");
        return LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT;
    }
    *out_namespace = nullptr;

    if (error != nullptr) {
        error->code = LANCE_NAMESPACE_STATUS_OK;
        std::free(error->message);
        error->message = nullptr;
    }

    if (impl == nullptr || impl[0] == '\0') {
        set_error(error, LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT, "impl must not be empty");
        return LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT;
    }

    lance_namespace_create_fn create_fn = nullptr;
    {
        std::lock_guard<std::mutex> lock(registry_mutex());
        auto it = registry().find(impl);
        if (it == registry().end()) {
            set_error(error, LANCE_NAMESPACE_STATUS_NOT_FOUND, std::string("unknown namespace implementation: ") + impl);
            return LANCE_NAMESPACE_STATUS_NOT_FOUND;
        }
        create_fn = it->second.create_fn;
    }

    if (create_fn == nullptr) {
        set_error(error, LANCE_NAMESPACE_STATUS_NOT_FOUND, std::string("unknown namespace implementation: ") + impl);
        return LANCE_NAMESPACE_STATUS_NOT_FOUND;
    }

    lance_namespace_handle_t *handle = nullptr;
    auto status = create_fn(options, &handle, error);
    if (status != LANCE_NAMESPACE_STATUS_OK) {
        return status;
    }

    if (handle == nullptr) {
        set_error(error, LANCE_NAMESPACE_STATUS_INTERNAL, std::string("namespace factory returned null: ") + impl);
        return LANCE_NAMESPACE_STATUS_INTERNAL;
    }

    *out_namespace = handle;
    return LANCE_NAMESPACE_STATUS_OK;
}

extern "C" void lance_namespace_error_free(lance_namespace_error_t *error) {
    if (error == nullptr) {
        return;
    }

    std::free(error->message);
    error->message = nullptr;
}

// Helper functions for model types

extern "C" lance_namespace_map_t lance_namespace_map_create(size_t capacity) {
    lance_namespace_map_t map;
    if (capacity == 0) {
        map.keys = nullptr;
        map.values = nullptr;
        map.count = 0;
        return map;
    }

    map.keys = static_cast<const char **>(std::calloc(capacity, sizeof(const char *)));
    map.values = static_cast<const char **>(std::calloc(capacity, sizeof(const char *)));
    map.count = 0;
    return map;
}

extern "C" void lance_namespace_map_free(lance_namespace_map_t *map) {
    if (map == nullptr) {
        return;
    }

    if (map->keys != nullptr) {
        for (size_t i = 0; i < map->count; ++i) {
            std::free(const_cast<char *>(map->keys[i]));
        }
        std::free(map->keys);
        map->keys = nullptr;
    }

    if (map->values != nullptr) {
        for (size_t i = 0; i < map->count; ++i) {
            std::free(const_cast<char *>(map->values[i]));
        }
        std::free(map->values);
        map->values = nullptr;
    }

    map->count = 0;
}

extern "C" int lance_namespace_map_set(lance_namespace_map_t *map, const char *key, const char *value) {
    if (map == nullptr || key == nullptr) {
        return 0;
    }

    // Check if key already exists
    for (size_t i = 0; i < map->count; ++i) {
        if (std::strcmp(map->keys[i], key) == 0) {
            std::free(const_cast<char *>(map->values[i]));
            map->values[i] = value ? strdup(value) : nullptr;
            return 1;
        }
    }

    // Add new key-value pair
    char *key_copy = strdup(key);
    char *value_copy = value ? strdup(value) : nullptr;

    if (key_copy == nullptr || (value != nullptr && value_copy == nullptr)) {
        std::free(key_copy);
        std::free(value_copy);
        return 0;
    }

    // Resize arrays if needed (simple approach - could be optimized)
    const char **new_keys = static_cast<const char **>(
        std::realloc(map->keys, (map->count + 1) * sizeof(const char *)));
    const char **new_values = static_cast<const char **>(
        std::realloc(map->values, (map->count + 1) * sizeof(const char *)));

    if (new_keys == nullptr || new_values == nullptr) {
        std::free(key_copy);
        std::free(value_copy);
        return 0;
    }

    map->keys = new_keys;
    map->values = new_values;
    map->keys[map->count] = key_copy;
    map->values[map->count] = value_copy;
    map->count++;

    return 1;
}

extern "C" lance_namespace_buffer_t lance_namespace_buffer_create(size_t size) {
    lance_namespace_buffer_t buffer;
    if (size == 0) {
        buffer.data = nullptr;
        buffer.size = 0;
        return buffer;
    }

    buffer.data = static_cast<uint8_t *>(std::calloc(size, sizeof(uint8_t)));
    buffer.size = buffer.data != nullptr ? size : 0;
    return buffer;
}

extern "C" void lance_namespace_buffer_free(lance_namespace_buffer_t *buffer) {
    if (buffer == nullptr) {
        return;
    }

    if (buffer->data != nullptr) {
        std::free(buffer->data);
        buffer->data = nullptr;
    }
    buffer->size = 0;
}

extern "C" const char *lance_namespace_error_code_to_string(
    lance_namespace_error_code_t code) {
    switch (code) {
        case LANCE_NAMESPACE_ERROR_UNSUPPORTED:
            return "unsupported";
        case LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_FOUND:
            return "namespace_not_found";
        case LANCE_NAMESPACE_ERROR_NAMESPACE_ALREADY_EXISTS:
            return "namespace_already_exists";
        case LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_EMPTY:
            return "namespace_not_empty";
        case LANCE_NAMESPACE_ERROR_TABLE_NOT_FOUND:
            return "table_not_found";
        case LANCE_NAMESPACE_ERROR_TABLE_ALREADY_EXISTS:
            return "table_already_exists";
        case LANCE_NAMESPACE_ERROR_TABLE_INDEX_NOT_FOUND:
            return "table_index_not_found";
        case LANCE_NAMESPACE_ERROR_TABLE_INDEX_ALREADY_EXISTS:
            return "table_index_already_exists";
        case LANCE_NAMESPACE_ERROR_TABLE_TAG_NOT_FOUND:
            return "table_tag_not_found";
        case LANCE_NAMESPACE_ERROR_TABLE_TAG_ALREADY_EXISTS:
            return "table_tag_already_exists";
        case LANCE_NAMESPACE_ERROR_TRANSACTION_NOT_FOUND:
            return "transaction_not_found";
        case LANCE_NAMESPACE_ERROR_TABLE_VERSION_NOT_FOUND:
            return "table_version_not_found";
        case LANCE_NAMESPACE_ERROR_TABLE_COLUMN_NOT_FOUND:
            return "table_column_not_found";
        case LANCE_NAMESPACE_ERROR_INVALID_INPUT:
            return "invalid_input";
        case LANCE_NAMESPACE_ERROR_CONCURRENT_MODIFICATION:
            return "concurrent_modification";
        case LANCE_NAMESPACE_ERROR_PERMISSION_DENIED:
            return "permission_denied";
        case LANCE_NAMESPACE_ERROR_UNAUTHENTICATED:
            return "unauthenticated";
        case LANCE_NAMESPACE_ERROR_SERVICE_UNAVAILABLE:
            return "service_unavailable";
        case LANCE_NAMESPACE_ERROR_INTERNAL:
            return "internal";
        case LANCE_NAMESPACE_ERROR_INVALID_TABLE_STATE:
            return "invalid_table_state";
        case LANCE_NAMESPACE_ERROR_TABLE_SCHEMA_VALIDATION_ERROR:
            return "table_schema_validation_error";
        case LANCE_NAMESPACE_ERROR_THROTTLING:
            return "throttling";
        case LANCE_NAMESPACE_ERROR_TABLE_BRANCH_NOT_FOUND:
            return "table_branch_not_found";
        case LANCE_NAMESPACE_ERROR_TABLE_BRANCH_ALREADY_EXISTS:
            return "table_branch_already_exists";
        default:
            return "internal";
    }
}
