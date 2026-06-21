/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

/**
 * @file apis.hpp
 * @brief expose lance_namespace_rest_client::api as lance_namespace::apis
 */

#pragma once

#include "lance_namespace_rest_client/AnyType.h"
#include "lance_namespace_rest_client/ApiClient.h"
#include "lance_namespace_rest_client/ApiConfiguration.h"
#include "lance_namespace_rest_client/ApiException.h"
#include "lance_namespace_rest_client/api/BranchApi.h"
#include "lance_namespace_rest_client/api/DataApi.h"
#include "lance_namespace_rest_client/api/IndexApi.h"
#include "lance_namespace_rest_client/api/MaterializedViewApi.h"
#include "lance_namespace_rest_client/api/MetadataApi.h"
#include "lance_namespace_rest_client/api/NamespaceApi.h"
#include "lance_namespace_rest_client/api/TableApi.h"
#include "lance_namespace_rest_client/api/TagApi.h"
#include "lance_namespace_rest_client/api/TransactionApi.h"

namespace lance_namespace {

namespace apis = org::openapitools::client::api;

}  // namespace lance_namespace
