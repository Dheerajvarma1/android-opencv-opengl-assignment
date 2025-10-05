#include <jni.h>
#include <opencv2/opencv.hpp>
#include <android/log.h>

#define LOG_TAG "EdgeDetection-Native"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

using namespace cv;

extern "C" {

/**
 * JNI function to detect edges using Canny edge detection
 * This is the same algorithm from your desktop version, adapted for Android!
 */
JNIEXPORT void JNICALL
Java_com_edgedetection_EdgeDetector_detectEdges(
        JNIEnv *env,
        jclass clazz,
        jlong inputAddr,
        jlong outputAddr,
        jint lowerThreshold,
        jint upperThreshold,
        jint blurSize) {

    try {
        // Get Mat objects from native addresses
        Mat &input = *(Mat *) inputAddr;
        Mat &output = *(Mat *) outputAddr;

        // Convert RGBA to Grayscale
        Mat gray;
        cvtColor(input, gray, COLOR_RGBA2GRAY);

        // Ensure blur size is odd and at least 1
        int kernelSize = blurSize;
        if (kernelSize < 1) {
            kernelSize = 1;
        }
        if (kernelSize % 2 == 0) {
            kernelSize += 1;
        }

        // Apply Gaussian blur to reduce noise
        Mat blurred;
        GaussianBlur(gray, blurred, Size(kernelSize, kernelSize), 0);

        // Apply Canny edge detection
        Mat edges;
        Canny(blurred, edges, lowerThreshold, upperThreshold);

        // Convert edges back to RGBA for display
        cvtColor(edges, output, COLOR_GRAY2RGBA);

        // Optional: Add parameter text overlay on the output
        String lowerText = "Lower: " + std::to_string(lowerThreshold);
        String upperText = "Upper: " + std::to_string(upperThreshold);
        String blurText = "Blur: " + std::to_string(kernelSize);

        putText(output, lowerText, Point(10, 50),
                FONT_HERSHEY_SIMPLEX, 1.0, Scalar(0, 255, 0, 255), 2);
        putText(output, upperText, Point(10, 100),
                FONT_HERSHEY_SIMPLEX, 1.0, Scalar(0, 255, 0, 255), 2);
        putText(output, blurText, Point(10, 150),
                FONT_HERSHEY_SIMPLEX, 1.0, Scalar(0, 255, 0, 255), 2);

        // Clean up temporary matrices
        gray.release();
        blurred.release();
        edges.release();

    } catch (cv::Exception &e) {
        LOGE("OpenCV Error: %s", e.what());
    } catch (...) {
        LOGE("Unknown error in edge detection");
    }
}

} // extern "C"

