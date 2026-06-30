// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

/**
 * Tests for the C API (lance_namespace.h + lance_namespace_impl.h).
 */

#include <lance_namespace/lance_namespace_impl.h>

#include <gtest/gtest.h>
#include <cstdlib>
#include <cstring>
#include <string>

/* ─── Minimal in-process implementation via C vtable ─────────────────────── */

namespace {

struct MockData {
  std::string id;
};

static void mock_destroy(void *impl) { delete static_cast<MockData *>(impl); }

static const char *mock_id(const void *impl) {
  return static_cast<const MockData *>(impl)->id.c_str();
}

static char *mock_list_namespaces(void * /*impl*/, const char * /*req*/) {
  return strdup(R"({"namespaces":[]})");
}

static char *mock_list_tables(void * /*impl*/, const char * /*req*/) {
  return strdup(R"({"tables":[]})");
}

static int mock_namespace_exists(void * /*impl*/, const char * /*req*/) { return 0; }

static int64_t mock_count_table_rows(void * /*impl*/, const char * /*req*/) { return 42; }

static lance_namespace_bytes_t *mock_query_table(void * /*impl*/, const char * /*req*/) {
  auto *b = static_cast<lance_namespace_bytes_t *>(std::malloc(sizeof(lance_namespace_bytes_t)));
  b->data = static_cast<uint8_t *>(std::malloc(4));
  std::memcpy(b->data, "ARWF", 4);
  b->length = 4;
  return b;
}

static const lance_namespace_vtable_t MOCK_VTABLE = {
    .abi_version = LANCE_NAMESPACE_ABI_VERSION,
    .destroy = mock_destroy,
    .namespace_id = mock_id,
    .list_namespaces = mock_list_namespaces,
    .namespace_exists = mock_namespace_exists,
    .list_tables = mock_list_tables,
    .count_table_rows = mock_count_table_rows,
    .query_table = mock_query_table,
};

lance_namespace_t *mock_factory(const lance_namespace_properties_t *props) {
  auto *d = new MockData;
  d->id = "mock";
  if (props) {
    for (size_t i = 0; i < props->count; ++i) {
      if (std::strcmp(props->items[i].key, "id") == 0) d->id = props->items[i].value;
    }
  }
  return lance_namespace_create_from_vtable(&MOCK_VTABLE, d);
}

}  // namespace

/* ─── Fixture ─────────────────────────────────────────────────────────────── */

class CApiTest : public ::testing::Test {
 protected:
  void SetUp() override { lance_namespace_register("mock", mock_factory); }
};

/* ─── connect / destroy ──────────────────────────────────────────────────── */

TEST_F(CApiTest, ConnectRegisteredType) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  lance_namespace_destroy(ns);
}

TEST_F(CApiTest, ConnectWithProperties) {
  lance_namespace_property_t pa[] = {{"id", "my-ns"}};
  lance_namespace_properties_t p{pa, 1};
  lance_namespace_t *ns = lance_namespace_connect("mock", &p);
  ASSERT_NE(ns, nullptr);
  EXPECT_STREQ(lance_namespace_id(ns), "my-ns");
  lance_namespace_destroy(ns);
}

TEST_F(CApiTest, DestroyNullIsNoOp) { lance_namespace_destroy(nullptr); }

/* ─── Unknown / null impl type ───────────────────────────────────────────── */

TEST_F(CApiTest, UnknownTypeReturnsNull) {
  EXPECT_EQ(lance_namespace_connect("no-such", nullptr), nullptr);
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_ERR_UNSUPPORTED);
}

TEST_F(CApiTest, NullImplTypeReturnsNull) {
  EXPECT_EQ(lance_namespace_connect(nullptr, nullptr), nullptr);
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_ERR_INVALID_INPUT);
}

/* ─── Identity ───────────────────────────────────────────────────────────── */

TEST_F(CApiTest, NamespaceId) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  EXPECT_STREQ(lance_namespace_id(ns), "mock");
  lance_namespace_destroy(ns);
}

TEST_F(CApiTest, NamespaceIdNullHandleReturnsNull) {
  EXPECT_EQ(lance_namespace_id(nullptr), nullptr);
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_ERR_INVALID_INPUT);
}

/* ─── Namespace operations ───────────────────────────────────────────────── */

TEST_F(CApiTest, ListNamespacesReturnsJson) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  char *resp = lance_namespace_list_namespaces(ns, R"({"parent":[]})");
  ASSERT_NE(resp, nullptr);
  EXPECT_STREQ(resp, R"({"namespaces":[]})");
  lance_namespace_free_string(resp);
  lance_namespace_destroy(ns);
}

TEST_F(CApiTest, NamespaceExistsReturnsZero) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  EXPECT_EQ(lance_namespace_namespace_exists(ns, "{}"), 0);
  lance_namespace_destroy(ns);
}

TEST_F(CApiTest, UnsupportedOperationSetsError) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  char *resp = lance_namespace_describe_namespace(ns, "{}");
  EXPECT_EQ(resp, nullptr);
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_ERR_UNSUPPORTED);
  lance_namespace_destroy(ns);
}

/* ─── Table operations ───────────────────────────────────────────────────── */

TEST_F(CApiTest, ListTablesReturnsJson) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  char *resp = lance_namespace_list_tables(ns, R"({"namespace":[]})");
  ASSERT_NE(resp, nullptr);
  EXPECT_STREQ(resp, R"({"tables":[]})");
  lance_namespace_free_string(resp);
  lance_namespace_destroy(ns);
}

TEST_F(CApiTest, CountTableRowsReturnsI64) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  EXPECT_EQ(lance_namespace_count_table_rows(ns, "{}"), 42);
  lance_namespace_destroy(ns);
}

TEST_F(CApiTest, QueryTableReturnsBytesObject) {
  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  lance_namespace_bytes_t *b = lance_namespace_query_table(ns, "{}");
  ASSERT_NE(b, nullptr);
  EXPECT_EQ(b->length, 4u);
  EXPECT_EQ(std::memcmp(b->data, "ARWF", 4), 0);
  lance_namespace_free_bytes(b);
  lance_namespace_destroy(ns);
}

/* ─── Error state clears on success ─────────────────────────────────────── */

TEST_F(CApiTest, ErrorClearsOnSuccess) {
  lance_namespace_connect("no-such", nullptr);
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_ERR_UNSUPPORTED);

  lance_namespace_t *ns = lance_namespace_connect("mock", nullptr);
  ASSERT_NE(ns, nullptr);
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_OK);
  lance_namespace_destroy(ns);
}

/* ─── Error codes ────────────────────────────────────────────────────────── */

TEST(ErrorCodeTest, ValuesMatchPythonSDK) {
  EXPECT_EQ(LANCE_NS_ERR_UNSUPPORTED, 0);
  EXPECT_EQ(LANCE_NS_ERR_NAMESPACE_NOT_FOUND, 1);
  EXPECT_EQ(LANCE_NS_ERR_NAMESPACE_ALREADY_EXISTS, 2);
  EXPECT_EQ(LANCE_NS_ERR_TABLE_NOT_FOUND, 4);
  EXPECT_EQ(LANCE_NS_ERR_TABLE_ALREADY_EXISTS, 5);
  EXPECT_EQ(LANCE_NS_ERR_INVALID_INPUT, 13);
  EXPECT_EQ(LANCE_NS_ERR_INTERNAL, 18);
  EXPECT_EQ(LANCE_NS_ERR_TABLE_BRANCH_NOT_FOUND, 22);
  EXPECT_EQ(LANCE_NS_ERR_TABLE_BRANCH_ALREADY_EXISTS, 23);
}

/* ─── lance_namespace_impl_set_error helper ──────────────────────────────── */

TEST(ImplHelperTest, SetErrorIsVisibleViaPublicAPI) {
  lance_namespace_impl_set_error(LANCE_NS_ERR_INTERNAL, "test error");
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_ERR_INTERNAL);
  EXPECT_STREQ(lance_namespace_last_error_message(), "test error");
}

