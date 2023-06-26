cmake_minimum_required(VERSION 3.20)

# project name
project("anifly")

# detecting platform for compile
if(CMAKE_SYSTEM_NAME STREQUAL "Windows")
    # Windows Platform
    set(compile_platform ani_winnt)
elseif(CMAKE_SYSTEM_NAME STREQUAL "Linux")
    # Linux Platform
    set(compile_platform ani_linux)
elseif(CMAKE_SYSTEM_NAME STREQUAL "Darwin")
    # MacOS PlatForm 
    set(compile_platform ani_darwin)
elseif(ANDROID) 
    # Android Platform
    set(compile_platform ani_android)
else()
    message(FATAL_ERROR "PlatForm Not Support")
endif()


# for auto searching soruces
file(GLOB_RECURSE src "src/*.h" "src/*.hpp" "src/*.cpp" "src/*.cpp")
message(${src})
add_library("anifly" SHARED
        anifly.cpp
    ${src}
)

add_subdirectory(libs/HTTPRequest)

if(DEFINED ANDROID_ABI)
    set(ANDROID_ABI arm64-v8a)  # 替换为您想要的 ABI
endif()


find_library( log-lib
              log )

# for adding include directories
target_include_directories("anifly" PRIVATE
    ${CMAKE_SOURCE_DIR}/src
    ${CMAKE_SOURCE_DIR}/libs/HTTPRequest/include
    ${CMAKE_SOURCE_DIR}/libs/spdlog/include

    ${CMAKE_SOURCE_DIR}/libs/htmlparser
    # for more
)

# for link libraries
target_link_libraries("anifly" PRIVATE
    HTTPRequest    
    ${log-lib} 
    # lib name
)

target_compile_definitions("anifly" PRIVATE
    ${compile_platform}
    Normal
)
