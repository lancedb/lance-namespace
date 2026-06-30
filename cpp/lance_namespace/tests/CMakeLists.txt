# SPDX-License-Identifier: Apache-2.0
# SPDX-FileCopyrightText: Copyright The Lance Authors

cmake_minimum_required(VERSION 3.22)

include(FetchContent)
FetchContent_Declare(
    googletest
    GIT_REPOSITORY https://github.com/google/googletest.git
    GIT_TAG        v1.15.2
    FIND_PACKAGE_ARGS NAMES GTest)
# On Windows, prevent overriding the parent project's compiler/linker settings
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

add_executable(lance_namespace_tests
    test_c_api.cpp
    test_cxx_api.cpp)

target_link_libraries(lance_namespace_tests
    PRIVATE
        LanceNamespace::lance_namespace
        GTest::gtest_main)

if(MSVC)
    target_compile_options(lance_namespace_tests PRIVATE
        /W4 /WX /wd4251
        /D_CRT_SECURE_NO_WARNINGS /DNOMINMAX /DWIN32_LEAN_AND_MEAN)
else()
    target_compile_options(lance_namespace_tests PRIVATE
        -Wall -Wextra -Werror -Wpedantic
        # Test vtables intentionally leave most function pointers NULL via
        # designated initializers; suppress the spurious GCC warning.
        -Wno-missing-field-initializers)
endif()

include(GoogleTest)
# Use add_test for static registration (works without running the binary first)
add_test(NAME lance_namespace_tests COMMAND lance_namespace_tests)
