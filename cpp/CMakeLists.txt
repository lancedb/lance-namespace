# SPDX-License-Identifier: Apache-2.0
# SPDX-FileCopyrightText: Copyright The Lance Authors

cmake_minimum_required(VERSION 3.22)

project(LanceNamespace
    VERSION 0.8.7
    DESCRIPTION "C/C++ SDK for the Lance Namespace interface"
    HOMEPAGE_URL "https://github.com/lance-format/lance-namespace"
    LANGUAGES C CXX
)

# ─── Standards — match lance-c ───────────────────────────────────────────────
if(NOT DEFINED CMAKE_C_STANDARD OR CMAKE_C_STANDARD LESS 11)
    set(CMAKE_C_STANDARD 11)
endif()
set(CMAKE_C_STANDARD_REQUIRED ON)
set(CMAKE_C_EXTENSIONS OFF)

if(NOT DEFINED CMAKE_CXX_STANDARD OR CMAKE_CXX_STANDARD LESS 20)
    set(CMAKE_CXX_STANDARD 20)
endif()
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# ─── Default build type ───────────────────────────────────────────────────────
if(NOT CMAKE_BUILD_TYPE AND NOT CMAKE_CONFIGURATION_TYPES)
    set(CMAKE_BUILD_TYPE Release CACHE STRING "Build type" FORCE)
endif()

# ─── Options ─────────────────────────────────────────────────────────────────
option(LANCE_NAMESPACE_BUILD_SHARED "Build lance_namespace as a shared library" OFF)
option(LANCE_NAMESPACE_BUILD_TESTS  "Build unit tests"                          ON)

if(LANCE_NAMESPACE_BUILD_TESTS)
    enable_testing()
endif()

# ─── Subdirectories ──────────────────────────────────────────────────────────
add_subdirectory(lance_namespace)

if(IS_DIRECTORY "${CMAKE_CURRENT_LIST_DIR}/lance_namespace_rest_client" AND
   EXISTS "${CMAKE_CURRENT_LIST_DIR}/lance_namespace_rest_client/CMakeLists.txt")
    add_subdirectory(lance_namespace_rest_client)
endif()
