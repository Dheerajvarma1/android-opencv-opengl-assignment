# Android Edge Detection App

A real-time edge detection Android application using OpenCV with C++ native code and Java UI.

## Features

- **Real-time edge detection** using Canny algorithm
- **Live camera preview** with edge detection overlay
- **Adjustable parameters** via sliders (blur, thresholds)
- **Native C++ performance** for fast image processing
- **JNI integration** between Java UI and C++ OpenCV

## Architecture

- **Java Layer**: Android UI, camera handling, user interactions
- **C++ Layer**: OpenCV edge detection algorithms
- **JNI Bridge**: Connects Java and C++ layers

## Prerequisites

- Android Studio
- Android SDK with NDK 29.0.14033849
- OpenCV Android SDK (extracted to `C:/OpenCV-android-sdk/`)
- Android device or emulator

## Project Structure

```
app/
├── src/main/
│   ├── java/com/edgedetection/
│   │   ├── MainActivity.java          # Main Android activity
│   │   └── EdgeDetector.java          # JNI bridge class
│   ├── cpp/
│   │   ├── edge_detection.cpp         # C++ OpenCV code
│   │   └── CMakeLists.txt             # CMake configuration
│   ├── jniLibs/                       # OpenCV native libraries
│   └── res/                           # Android resources
├── build.gradle                       # App module configuration
└── AndroidManifest.xml                # App manifest
```

## How to Run

1. **Open in Android Studio**
   - File → Open → Select this project folder

2. **Sync Project**
   - File → Sync Project with Gradle Files

3. **Run the App**
   - Connect Android device or start emulator
   - Click the green Run button (▶️)

## Usage

1. **Grant camera permission** when prompted
2. **Adjust parameters** using the sliders:
   - **Blur**: Controls Gaussian blur intensity
   - **Lower Threshold**: Minimum edge strength
   - **Upper Threshold**: Maximum edge strength
3. **View edge detection** in real-time on camera feed

## Technical Details

- **Edge Detection**: Canny algorithm
- **Image Processing**: OpenCV 4.12.0
- **NDK Version**: 29.0.14033849
- **Target SDK**: Android 14 (API 34)
- **Min SDK**: Android 7.0 (API 24)

## Troubleshooting

- **Camera permission**: Ensure camera permission is granted
- **OpenCV loading**: Check Logcat for OpenCV initialization errors
- **NDK issues**: Verify NDK 29.0.14033849 is installed

## Dependencies

- OpenCV Android SDK
- Android NDK
- CMake
- JNI (Java Native Interface)

---

**Built with ❤️ using Java, C++, OpenCV, and JNI**
