cmake_minimum_required(VERSION 3.20)

project(lance_namespace_cpp LANGUAGES CXX)

include(CTest)

option(BUILD_TESTING "Build tests" ON)

add_library(lance_namespace SHARED
    src/lance_namespace.cpp
    src/operations.cpp
)

target_compile_features(lance_namespace PUBLIC cxx_std_17)
target_include_directories(lance_namespace
    PUBLIC
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/../lance_namespace_rest_client/include>
        $<INSTALL_INTERFACE:include>
)
target_compile_definitions(lance_namespace PRIVATE LANCE_NAMESPACE_BUILDING=1)

add_executable(lance_namespace_smoke tests/smoke.cpp)
target_link_libraries(lance_namespace_smoke PRIVATE lance_namespace)

if(BUILD_TESTING)
    add_test(NAME lance_namespace_smoke COMMAND lance_namespace_smoke)
endif()

install(TARGETS lance_namespace
    LIBRARY DESTINATION lib
    RUNTIME DESTINATION bin
)
install(DIRECTORY include/ DESTINATION include)
