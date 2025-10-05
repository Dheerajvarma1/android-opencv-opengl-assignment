@echo off
"C:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Elden ring dlc\\clones\\android-opencv-opengl-assignment\\app\\app\\src\\main\\cpp" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=24" ^
  "-DANDROID_PLATFORM=android-24" ^
  "-DANDROID_ABI=arm64-v8a" ^
  "-DCMAKE_ANDROID_ARCH_ABI=arm64-v8a" ^
  "-DANDROID_NDK=C:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk\\ndk\\29.0.14033849" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk\\ndk\\29.0.14033849" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk\\ndk\\29.0.14033849\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\ASUS\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_CXX_FLAGS=-frtti -fexceptions" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Elden ring dlc\\clones\\android-opencv-opengl-assignment\\app\\app\\build\\intermediates\\cxx\\Debug\\4c3r2w54\\obj\\arm64-v8a" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Elden ring dlc\\clones\\android-opencv-opengl-assignment\\app\\app\\build\\intermediates\\cxx\\Debug\\4c3r2w54\\obj\\arm64-v8a" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BC:\\Elden ring dlc\\clones\\android-opencv-opengl-assignment\\app\\app\\.cxx\\Debug\\4c3r2w54\\arm64-v8a" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
