// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

/**
 * Tests for the C++ wrapper API (lance_namespace.hpp).
 */

#include <lance_namespace/lance_namespace.hpp>
#include <lance_namespace/lance_namespace_impl.h>

#include <cstdlib>
#include <cstring>
#include <gtest/gtest.h>
#include <string>

/* ─── Minimal implementation via C vtable ─────────────────────────────────── */

namespace {

struct CppMockData {
  std::string id;
};

static void cpp_destroy(void *impl) {
  delete static_cast<CppMockData *>(impl);
}
static const char *cpp_id(const void *impl) {
  return static_cast<const CppMockData *>(impl)->id.c_str();
}
static char *cpp_list_ns(void * /*impl*/, const char * /*req*/) {
  return strdup(R"({"namespaces":["a","b"]})");
}
static char *cpp_list_tables(void * /*impl*/, const char * /*req*/) {
  return strdup(R"({"tables":["t1"]})");
}
static int cpp_ns_exists(void * /*impl*/, const char * /*req*/) {
  return 0;
}
static int64_t cpp_count_rows(void * /*impl*/, const char * /*req*/) {
  return 7;
}
static lance_namespace_bytes_t *cpp_query(void * /*impl*/, const char * /*req*/) {
  auto *b = static_cast<lance_namespace_bytes_t *>(std::malloc(sizeof(lance_namespace_bytes_t)));
  b->data = static_cast<uint8_t *>(std::malloc(3));
  std::memcpy(b->data, "IPC", 3);
  b->length = 3;
  return b;
}

static const lance_namespace_vtable_t CPP_MOCK_VTABLE = {
    .abi_version = LANCE_NAMESPACE_ABI_VERSION,
    .destroy = cpp_destroy,
    .namespace_id = cpp_id,
    .list_namespaces = cpp_list_ns,
    .namespace_exists = cpp_ns_exists,
    .list_tables = cpp_list_tables,
    .count_table_rows = cpp_count_rows,
    .query_table = cpp_query,
};

lance_namespace_t *cpp_mock_factory(const lance_namespace_properties_t *props) {
  auto *d = new CppMockData;
  d->id = "cpp-mock";
  if (props) {
    for (size_t i = 0; i < props->count; ++i) {
      if (std::strcmp(props->items[i].key, "id") == 0)
        d->id = props->items[i].value;
    }
  }
  return lance_namespace_create_from_vtable(&CPP_MOCK_VTABLE, d);
}

}  // namespace

/* ─── Fixture ─────────────────────────────────────────────────────────────── */

class CxxApiTest : public ::testing::Test {
 protected:
  void SetUp() override { lance::namespace_::register_impl("cpp-mock", cpp_mock_factory); }
};

/* ─── Properties ─────────────────────────────────────────────────────────── */

TEST(PropertiesTest, Empty) {
  EXPECT_EQ(lance::namespace_::Properties{}.c_props()->count, 0u);
}

TEST(PropertiesTest, InitList) {
  lance::namespace_::Properties p{{"k1", "v1"}, {"k2", "v2"}};
  ASSERT_EQ(p.c_props()->count, 2u);
  EXPECT_STREQ(p.c_props()->items[0].key, "k1");
}

TEST(PropertiesTest, MapConstructor) {
  lance::namespace_::Properties p(std::unordered_map<std::string, std::string>{{"a", "1"}});
  EXPECT_EQ(p.c_props()->count, 1u);
}

TEST(PropertiesTest, Set) {
  lance::namespace_::Properties p;
  p.set("uri", "http://localhost");
  EXPECT_EQ(p.c_props()->count, 1u);
}

/* ─── connect / Namespace ────────────────────────────────────────────────── */

TEST_F(CxxApiTest, ConnectReturnsValidHandle) {
  auto ns = lance::namespace_::connect("cpp-mock");
  EXPECT_TRUE(static_cast<bool>(ns));
}

TEST_F(CxxApiTest, ConnectPassesId) {
  lance::namespace_::Properties p{{"id", "test-ns"}};
  auto ns = lance::namespace_::connect("cpp-mock", p);
  EXPECT_EQ(ns.id(), "test-ns");
}

TEST_F(CxxApiTest, ConnectUnknownThrows) {
  EXPECT_THROW(lance::namespace_::connect("no-impl"), lance::namespace_::Error);
}

/* ─── Namespace operations ───────────────────────────────────────────────── */

TEST_F(CxxApiTest, ListNamespacesReturnsTyped) {
  auto ns = lance::namespace_::connect("cpp-mock");
  lance_namespace::models::ListNamespacesRequest req;
  auto resp = ns.list_namespaces(req);
  // The mock returns {"namespaces":["a","b"]}; verify it parses correctly.
  EXPECT_FALSE(resp.getNamespaces().empty());
}

TEST_F(CxxApiTest, NamespaceExistsDoesNotThrow) {
  auto ns = lance::namespace_::connect("cpp-mock");
  lance_namespace::models::NamespaceExistsRequest req;
  EXPECT_NO_THROW(ns.namespace_exists(req));
}

TEST_F(CxxApiTest, UnsupportedOpThrows) {
  auto ns = lance::namespace_::connect("cpp-mock");
  lance_namespace::models::DescribeNamespaceRequest req;
  EXPECT_THROW(ns.describe_namespace(req), lance::namespace_::Error);
  EXPECT_EQ(lance_namespace_last_error_code(), LANCE_NS_ERR_UNSUPPORTED);
}

/* ─── Table operations ───────────────────────────────────────────────────── */

TEST_F(CxxApiTest, ListTablesReturnsTyped) {
  auto ns = lance::namespace_::connect("cpp-mock");
  lance_namespace::models::ListTablesRequest req;
  auto resp = ns.list_tables(req);
  EXPECT_FALSE(resp.getTables().empty());
}

TEST_F(CxxApiTest, CountTableRowsReturnsI64) {
  auto ns = lance::namespace_::connect("cpp-mock");
  lance_namespace::models::CountTableRowsRequest req;
  EXPECT_EQ(ns.count_table_rows(req), 7);
}

TEST_F(CxxApiTest, QueryTableReturnsByteVector) {
  auto ns = lance::namespace_::connect("cpp-mock");
  lance_namespace::models::QueryTableRequest req;
  auto bytes = ns.query_table(req);
  ASSERT_EQ(bytes.size(), 3u);
  EXPECT_EQ(bytes[0], 'I');
  EXPECT_EQ(bytes[1], 'P');
  EXPECT_EQ(bytes[2], 'C');
}

/* ─── Move semantics ─────────────────────────────────────────────────────── */

TEST_F(CxxApiTest, MoveConstructor) {
  auto ns1 = lance::namespace_::connect("cpp-mock");
  auto ns2 = std::move(ns1);
  EXPECT_FALSE(static_cast<bool>(ns1));  // NOLINT(bugprone-use-after-move)
  EXPECT_TRUE(static_cast<bool>(ns2));
  EXPECT_EQ(ns2.id(), "cpp-mock");
}

TEST_F(CxxApiTest, MoveAssignment) {
  auto ns1 = lance::namespace_::connect("cpp-mock");
  lance::namespace_::Namespace ns2;
  ns2 = std::move(ns1);
  EXPECT_FALSE(static_cast<bool>(ns1));  // NOLINT(bugprone-use-after-move)
  EXPECT_TRUE(static_cast<bool>(ns2));
}

/* ─── Error type ─────────────────────────────────────────────────────────── */

TEST(ErrorTest, CodeAndMessage) {
  lance::namespace_::Error e("msg", LANCE_NS_ERR_TABLE_NOT_FOUND);
  EXPECT_EQ(e.code(), LANCE_NS_ERR_TABLE_NOT_FOUND);
  EXPECT_STREQ(e.what(), "msg");
}

TEST(ErrorTest, IsRuntimeError) {
  lance::namespace_::Error e("x", LANCE_NS_ERR_INTERNAL);
  EXPECT_NO_THROW({
    try {
      throw e;
    } catch (const std::runtime_error &re) {
      EXPECT_STREQ(re.what(), "x");
    }
  });
}
