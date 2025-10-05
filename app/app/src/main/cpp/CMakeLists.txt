cmake_minimum_required(VERSION 3.22.1)

project(edge_detection)

# Set C++ standard
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find OpenCV
set(OpenCV_DIR "C:/OpenCV-android-sdk/sdk/native/jni")
find_package(OpenCV REQUIRED)

# Add our library
add_library(edge_detection SHARED
        edge_detection.cpp)

# Link libraries
target_link_libraries(edge_detection
        ${OpenCV_LIBS}
        android
        log
        c++_shared
        GLESv2
        EGL)

message(STATUS "OpenCV found: ${OpenCV_FOUND}")
message(STATUS "OpenCV version: ${OpenCV_VERSION}")
message(STATUS "Building for ABI: ${ANDROID_ABI}")

