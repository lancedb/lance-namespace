#include <cassert>
#include <cstring>
#include <memory>
#include <string>

#include "lance_namespace/lance_namespace.h"
#include "lance_namespace/lance_namespace.hpp"

namespace {

struct CState {
    std::string id;
};

const char *c_namespace_id(const lance_namespace_handle_t *handle) {
    auto *state = static_cast<CState *>(lance_namespace_handle_instance(handle));
    return state ? state->id.c_str() : nullptr;
}

void c_destroy(void *instance) {
    delete static_cast<CState *>(instance);
}

lance_namespace_status_t c_create(
    const lance_namespace_options_t *options,
    lance_namespace_handle_t **out_namespace,
    lance_namespace_error_t *error) {
    const char *suffix = lance_namespace_option_get(options, "suffix");
    auto *state = new CState{std::string("c://") + (suffix ? suffix : "default")};
    static const lance_namespace_vtable_t vtable{c_namespace_id, c_destroy};
    auto *handle = lance_namespace_handle_create(&vtable, state);
    if (handle == nullptr) {
        if (error != nullptr) {
            error->code = LANCE_NAMESPACE_STATUS_INTERNAL;
        }
        delete state;
        return LANCE_NAMESPACE_STATUS_INTERNAL;
    }
    *out_namespace = handle;
    return LANCE_NAMESPACE_STATUS_OK;
}

class CppNamespace final : public lance_namespace::Namespace {
  public:
    explicit CppNamespace(std::string id) : id_(std::move(id)) {}

    std::string namespace_id() const override { return id_; }

  private:
    std::string id_;
};

void test_unsupported_operations() {
    auto ns = std::make_unique<CppNamespace>("test://ns");

    bool caught = false;
    try {
        ns->list_namespaces(nullptr);
    } catch (const lance_namespace::UnsupportedOperationError &e) {
        caught = true;
    }
    assert(caught);

    caught = false;
    try {
        ns->create_table(nullptr, {});
    } catch (const lance_namespace::UnsupportedOperationError &e) {
        caught = true;
    }
    assert(caught);

    caught = false;
    try {
        ns->query_table(nullptr);
    } catch (const lance_namespace::UnsupportedOperationError &e) {
        caught = true;
    }
    assert(caught);

    caught = false;
    try {
        ns->count_table_rows(nullptr);
    } catch (const lance_namespace::UnsupportedOperationError &e) {
        caught = true;
    }
    assert(caught);
}

}  // namespace

int main() {
    test_unsupported_operations();

    {
        auto status = lance_namespace_register_impl("c-dummy", c_create);
        assert(status == LANCE_NAMESPACE_STATUS_OK);

        lance_namespace_property_t properties[] = {
            {"suffix", "dummy"},
        };
        lance_namespace_options_t options{properties, 1};
        lance_namespace_handle_t *handle = nullptr;
        lance_namespace_error_t error{LANCE_NAMESPACE_STATUS_OK, nullptr};

        status = lance_namespace_connect("c-dummy", &options, &handle, &error);
        assert(status == LANCE_NAMESPACE_STATUS_OK);
        assert(handle != nullptr);
        assert(std::strcmp(lance_namespace_namespace_id(handle), "c://dummy") == 0);

        lance_namespace_handle_release(handle);
        lance_namespace_error_free(&error);
        status = lance_namespace_unregister_impl("c-dummy");
        assert(status == LANCE_NAMESPACE_STATUS_OK);
    }

    {
        lance_namespace_header_t headers[] = {
            {"x-lance-test", "passed"},
        };
        lance_namespace_headers_t header_list{headers, 1};
        assert(std::strcmp(lance_namespace_header_get(&header_list, "x-lance-test"), "passed") == 0);
        assert(lance_namespace_header_get(&header_list, "missing") == nullptr);
    }

    {
        lance_namespace::register_namespace_impl(
            "cpp-dummy",
            [](const lance_namespace::Properties &) {
                return std::make_unique<CppNamespace>("cpp://dummy");
            });

        auto namespace_ptr = lance_namespace::connect("cpp-dummy", {});
        assert(namespace_ptr != nullptr);
        assert(namespace_ptr->namespace_id() == "cpp://dummy");

        lance_namespace::unregister_namespace_impl("cpp-dummy");
    }

    return 0;
}
