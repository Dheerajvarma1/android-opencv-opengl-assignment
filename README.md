# Android Edge Detection App with OpenGL ES 2.0

A professional-grade real-time edge detection Android application featuring hardware-accelerated OpenGL ES 2.0 rendering with OpenCV C++ native processing.

## Features

- **Hardware-Accelerated Rendering** using OpenGL ES 2.0
- **Real-time edge detection** with Canny algorithm
- **Custom OpenGL ES Shaders** (vertex & fragment)
- **Live camera preview** with GPU-powered display
- **Adjustable parameters** via sliders (blur, thresholds)
- **Native C++ performance** for ultra-fast image processing
- **JNI integration** between Java UI and C++ OpenCV
- **Robust error handling** and crash prevention
- **Dual-camera architecture** (OpenCV capture + OpenGL ES display)
- **Professional-grade texture management**

## Screenshot

![Working App](https://github.com/user-attachments/assets/4ba5ad6f-cb3b-4aa5-9e4a-af8eb47e186f)

*Real-time edge detection app running on Android with OpenGL ES 2.0 hardware acceleration*

## Demo Video

https://github.com/user-attachments/assets/e4a335b4-bb84-4c83-a3b4-8e060cc9c440

*Watch the app in action - real-time edge detection with adjustable parameters*

## Live Demo Website

🌐 **[View Live Demo](https://edge-detection-website-miwe9bmq8-dheerajvarma1s-projects.vercel.app/)**

*Interactive web showcase featuring multiple edge detection examples with different parameters and real-time FPS metrics*

## Architecture

```
Camera → OpenCV Processing → OpenGL ES 2.0 Rendering → Screen
```

- **Java Layer**: Android UI, camera handling, OpenGL ES context management
- **C++ Layer**: OpenCV edge detection algorithms (Canny)
- **JNI Bridge**: Connects Java and C++ layers seamlessly
- **OpenGL ES 2.0**: Hardware-accelerated GPU rendering pipeline
- **Custom Shaders**: Vertex and fragment shaders for texture display
- **Texture Management**: Efficient Mat-to-texture conversion

## Prerequisites

- **Android Studio** (latest version recommended)
- **Android SDK** with NDK 29.0.14033849
- **OpenCV Android SDK** (extracted to `C:/OpenCV-android-sdk/`)
- **Android device** with OpenGL ES 2.0 support (or emulator)
- **Git** (for version control)

## Project Structure

```
android-opencv-opengl-assignment/
├── app/                                   # Android Application
│   ├── src/main/
│   │   ├── java/com/edgedetection/
│   │   │   ├── MainActivity.java              # Main OpenGL ES activity
│   │   │   ├── MainActivityFallback.java      # Fallback activity
│   │   │   ├── EdgeDetector.java              # JNI bridge class
│   │   │   └── opengl/
│   │   │       ├── EdgeDetectionGLView.java    # Custom GLSurfaceView
│   │   │       └── EdgeDetectionRenderer.java # OpenGL ES renderer
│   │   ├── assets/shaders/
│   │   │   ├── vertex_shader.glsl             # Vertex shader
│   │   │   └── fragment_shader.glsl           # Fragment shader
│   │   ├── cpp/
│   │   │   ├── edge_detection.cpp             # C++ OpenCV code
│   │   │   └── CMakeLists.txt                 # CMake configuration
│   │   ├── jniLibs/                           # OpenCV native libraries
│   │   └── res/                               # Android resources
│   ├── build.gradle                           # App module configuration
│   └── AndroidManifest.xml                    # App manifest
|
|── web/

## How to Run

### Option 1: OpenGL ES 2.0 Version (Recommended)
1. **Open in Android Studio**
   - File → Open → Select this project folder

2. **Sync Project**
   - File → Sync Project with Gradle Files

3. **Switch to OpenGL ES Version**
   - Edit `AndroidManifest.xml`
   - Change launcher activity to `MainActivity`

4. **Run the App**
   - Connect Android device with OpenGL ES 2.0 support
   - Click the green Run button (▶️)

### Option 2: Fallback Version (For Testing)
1. **Use Fallback Activity**
   - Keep `MainActivityFallback` as launcher in `AndroidManifest.xml`
   - This version uses standard OpenCV rendering

## Usage

1. **Grant camera permission** when prompted
2. **Choose your version**:
   - **OpenGL ES**: Hardware-accelerated, professional-grade rendering
   - **Fallback**: Standard OpenCV rendering for compatibility
3. **Adjust parameters** using the sliders:
   - **Blur**: Controls Gaussian blur intensity
   - **Lower Threshold**: Minimum edge strength  
   - **Upper Threshold**: Maximum edge strength

   ![Parameter Controls](https://github.com/user-attachments/assets/8e4e1ec2-7a08-435c-a087-bf0b0427bd44)
   
   *Interactive parameter sliders for real-time edge detection tuning*

4. **View edge detection** in real-time with GPU acceleration

## Technical Details

### Core Technologies
- **Edge Detection**: Canny algorithm with real-time processing
- **Image Processing**: OpenCV 4.12.0 with native C++ performance
- **Rendering**: OpenGL ES 2.0 with custom shaders
- **Architecture**: JNI bridge connecting Java UI to C++ processing

### Performance Specifications
- **NDK Version**: 29.0.14033849
- **Target SDK**: Android 14 (API 34)
- **Min SDK**: Android 7.0 (API 24)
- **OpenGL ES**: Version 2.0 (hardware acceleration required)
- **Rendering**: GPU-accelerated texture processing
- **Frame Rate**: Real-time processing with minimal latency

### OpenGL ES 2.0 Features
- **Custom Shaders**: Vertex and fragment shaders for texture rendering
- **Texture Management**: Efficient Mat-to-texture conversion
- **Hardware Acceleration**: GPU-powered rendering pipeline
- **Memory Optimization**: Professional-grade resource management
- **Error Handling**: Robust OpenGL ES error checking and recovery

## Troubleshooting

### Common Issues
- **Camera permission**: Ensure camera permission is granted
- **OpenCV loading**: Check Logcat for OpenCV initialization errors
- **NDK issues**: Verify NDK 29.0.14033849 is installed
- **OpenGL ES errors**: Check device OpenGL ES 2.0 support
- **Build failures**: Clean and rebuild project in Android Studio

### OpenGL ES Specific
- **Device compatibility**: Ensure device supports OpenGL ES 2.0
- **Shader compilation**: Check Logcat for shader compilation errors
- **Texture issues**: Verify Mat-to-texture conversion in logs
- **Context errors**: Check OpenGL ES context initialization

### Performance Optimization
- **Use OpenGL ES version** for best performance
- **Close background apps** to free GPU memory
- **Test on physical device** for accurate performance metrics

## Dependencies

### Core Libraries
- **OpenCV Android SDK** - Computer vision and image processing
- **Android NDK** - Native development kit for C++ integration
- **CMake** - Build system for native code
- **JNI** - Java Native Interface for Java-C++ communication

### OpenGL ES Libraries
- **GLESv2** - OpenGL ES 2.0 graphics library
- **EGL** - OpenGL ES context management
- **Custom Shaders** - Vertex and fragment shader programs

## Key Features Breakdown

### Hardware Acceleration
- GPU-powered rendering pipeline
- Custom OpenGL ES 2.0 shaders
- Efficient texture management
- Real-time frame processing

### Performance
- Native C++ OpenCV processing
- JNI-optimized data transfer
- Memory-efficient texture operations
- Minimal CPU overhead

### Reliability
- Comprehensive error handling
- Fallback rendering option
- Robust OpenGL ES context management
- Graceful degradation support

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/android-opencv-opengl-assignment.git
   ```

2. **Open in Android Studio**
   - Import the project
   - Sync with Gradle files

3. **Choose your version**
   - **OpenGL ES**: Edit `AndroidManifest.xml` → Set `MainActivity` as launcher
   - **Fallback**: Keep `MainActivityFallback` as launcher

4. **Run on device**
   - Connect Android device with OpenGL ES 2.0 support
   - Build and install the app

---

## Major Update: OpenGL ES 2.0 Implementation

This project now features a **complete OpenGL ES 2.0 hardware-accelerated rendering pipeline**! 

### What's New:
- **Custom OpenGL ES 2.0 shaders** for professional-grade rendering
- **Hardware-accelerated texture processing** with GPU optimization  
- **Dual-camera architecture** (OpenCV capture + OpenGL ES display)
- **Professional-grade error handling** and crash prevention
- **Fallback implementation** for compatibility testing
- **Real-time Mat-to-texture conversion** with memory optimization

### Performance Boost:
- **GPU-accelerated rendering** instead of CPU-based display
- **Hardware-accelerated texture operations** for smooth performance
- **Professional-grade graphics pipeline** with custom shaders
- **Optimized memory management** for mobile devices

**This is a major upgrade from basic OpenCV display to full OpenGL ES 2.0 hardware acceleration!**

---

**Built with Java, C++, OpenCV, OpenGL ES 2.0, and JNI**

---

# Web Viewer Component

A TypeScript-based web viewer for displaying processed camera frames from the Android OpenCV OpenGL assignment.

## Live Demo

🌐 **[View Live Demo](https://edge-detection-website-miwe9bmq8-dheerajvarma1s-projects.vercel.app/)**

*Interactive web showcase featuring multiple edge detection examples with different parameters and real-time FPS metrics*

## Project Structure

```
android-opencv-opengl-assignment/
├── app/     
|
|
└── web/                                   # Web Viewer Component
    ├── index.html                             # Main HTML page
    ├── src/
    │   ├── main.ts                           # TypeScript application logic
    │   └── styles.css                        # CSS styling
    ├── assets/
    │   ├── sample-frame.jpg                  # Sample processed frame
    │   └── README.md                         # Assets documentation
    ├── package.json                          # Dependencies and scripts
    ├── tsconfig.json                        # TypeScript configuration
    └── vite.config.ts                     # Vite build configuration
```

## Web Viewer Features

- **Image Display**: Load and display static processed frame images from Android app
- **Canvas Rendering**: Toggle between `<img>` and `<canvas>` rendering modes
- **Real-time Statistics**: Display FPS, lower count, Upper count, and Blur count
- **Interactive UI**: Minimal but functional interface with toggle controls
- **TypeScript**: Full type safety and modern JavaScript features
- **Responsive Design**: Works on desktop 

## Web Viewer Tech Stack

- **TypeScript** - Static type safety and better code maintainability
- **HTML5 & CSS3** - Modern web standards for structure and styling
- **Vite** - Fast build tool and development server
- **Canvas API** - For advanced image rendering and manipulation
- **No Backend** - Pure client-side application

## Web Viewer Setup

1. **Navigate to web directory**
   ```bash
   cd web
   ```

2. **Install Dependencies**
   ```bash
   npm install next react react-dom
   ```

3. **Start Development Server**
   ```bash
   npm run dev
   ```
   This will start the Vite development server at `http://localhost:3000`

4. **Build for Production**
   ```bash
   npm run build
   ```
   The built files will be in the `dist/` folder.

## Web Viewer Usage

1. **Open the web viewer** in your browser
2. **Load sample frame** - The application will automatically load the sample frame
3. **Toggle rendering modes** - Use the toggle button to switch between image and canvas rendering
4. **View statistics** - Check real-time statistics in the stats panel
5. **Add custom images** - Place processed frames from your Android app in the `assets/` directory

## Web Viewer API

The viewer exposes a global API for external interaction:

```typescript
// Access the viewer instance
const viewer = window.openCVViewer;

// Update frame statistics
viewer.updateFrameStats({
    fps: 30,
    resolution: '640x480',
    processingTime: 16.7,
    frameCount: 1000
});

// Load a custom image
viewer.loadCustomImage('./assets/my-frame.jpg');

// Get current statistics
const stats = viewer.getCurrentStats();
```

## Web Viewer Features Explained

### Image Display Modes
- **Image Mode**: Uses standard `<img>` element for simple display
- **Canvas Mode**: Uses `<canvas>` element for advanced rendering and effects

### Statistics Display
- **FPS**: Frames per second (simulated for demo)
- **Resolution**: Image dimensions in pixels
- **Processing Time**: Time taken to process each frame
- **Frame Count**: Total number of frames processed

### Overlay Information
Dynamic text overlay showing current status and image information.

## Web Viewer Development

### TypeScript Configuration
The project uses strict TypeScript settings for better code quality:
- Strict type checking enabled
- No unused variables/parameters allowed
- Modern ES2020 target with DOM support

### Build Process
- **Development**: `npm run dev` - Starts Vite dev server with hot reload
- **Production**: `npm run build` - Compiles TypeScript and bundles for production
- **Type Check**: `npm run type-check` - Validates TypeScript without emitting files

## Browser Support
- Chrome/Edge 88+
- Firefox 85+
- Safari 14+
- Mobile browsers with ES2020 support

---

**Web Viewer Built with TypeScript, Vite, HTML5, and Canvas API**
