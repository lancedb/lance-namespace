# SPDX-License-Identifier: Apache-2.0
# SPDX-FileCopyrightText: Copyright The Lance Authors

cmake_minimum_required(VERSION 3.22)

# ─── Library target ──────────────────────────────────────────────────────────
if(LANCE_NAMESPACE_BUILD_SHARED)
    add_library(lance_namespace SHARED
        src/lance_namespace.cpp)
    target_compile_definitions(lance_namespace
        PRIVATE LANCE_NAMESPACE_BUILD_SHARED)
    set_target_properties(lance_namespace PROPERTIES
        C_VISIBILITY_PRESET   hidden
        CXX_VISIBILITY_PRESET hidden
        VISIBILITY_INLINES_HIDDEN ON)
else()
    add_library(lance_namespace STATIC
        src/lance_namespace.cpp)
endif()

add_library(LanceNamespace::lance_namespace ALIAS lance_namespace)

target_include_directories(lance_namespace
    PUBLIC
        $<BUILD_INTERFACE:${CMAKE_CURRENT_LIST_DIR}/include>
        $<INSTALL_INTERFACE:include>)

# ─── Compiler warnings ───────────────────────────────────────────────────────
if(MSVC)
    target_compile_options(lance_namespace PRIVATE
        /W4
        /WX
        /wd4251   # exported class with non-exported member (acceptable for STL)
        /wd4275   # non-dll-interface base class used as base of dll-interface class
    )
    target_compile_definitions(lance_namespace PRIVATE
        _CRT_SECURE_NO_WARNINGS
        NOMINMAX
        WIN32_LEAN_AND_MEAN)
else()
    target_compile_options(lance_namespace PRIVATE
        -Wall -Wextra -Werror -Wpedantic)
endif()

# ─── Platform link requirements ──────────────────────────────────────────────
if(APPLE)
    target_link_libraries(lance_namespace INTERFACE
        "-framework CoreFoundation"
        "-framework IOKit"
        "-framework Security")
elseif(UNIX)
    target_link_libraries(lance_namespace INTERFACE pthread dl)
endif()

# ─── Tests ───────────────────────────────────────────────────────────────────
if(LANCE_NAMESPACE_BUILD_TESTS)
    add_subdirectory(tests)
endif()

# ─── Install rules ───────────────────────────────────────────────────────────
include(GNUInstallDirs)
include(CMakePackageConfigHelpers)

install(DIRECTORY include/lance_namespace
    DESTINATION ${CMAKE_INSTALL_INCLUDEDIR}
    FILES_MATCHING PATTERN "*.h" PATTERN "*.hpp")

install(TARGETS lance_namespace
    EXPORT LanceNamespaceTargets
    ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR})

install(EXPORT LanceNamespaceTargets
    FILE      LanceNamespaceTargets.cmake
    NAMESPACE LanceNamespace::
    DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/LanceNamespace)

configure_package_config_file(
    "${CMAKE_CURRENT_LIST_DIR}/cmake/LanceNamespaceConfig.cmake.in"
    "${CMAKE_CURRENT_BINARY_DIR}/LanceNamespaceConfig.cmake"
    INSTALL_DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/LanceNamespace)

write_basic_package_version_file(
    "${CMAKE_CURRENT_BINARY_DIR}/LanceNamespaceConfigVersion.cmake"
    VERSION     ${PROJECT_VERSION}
    COMPATIBILITY SameMinorVersion)

install(FILES
    "${CMAKE_CURRENT_BINARY_DIR}/LanceNamespaceConfig.cmake"
    "${CMAKE_CURRENT_BINARY_DIR}/LanceNamespaceConfigVersion.cmake"
    DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/LanceNamespace)
