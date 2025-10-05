package com.edgedetection.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import org.opencv.core.Mat;

public class EdgeDetectionGLView extends GLSurfaceView {
    
    private static final String TAG = "EdgeDetectionGLView";
    private EdgeDetectionRenderer mRenderer;
    
    public EdgeDetectionGLView(Context context) {
        super(context);
        init();
    }
    
    public EdgeDetectionGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init() {
        Log.d(TAG, "Initializing EdgeDetectionGLView");
        
        // Set OpenGL ES version to 2.0
        setEGLContextClientVersion(2);
        
        // Set renderer
        mRenderer = new EdgeDetectionRenderer(getContext());
        setRenderer(mRenderer);
        
        // Set render mode to continuous rendering
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        
        Log.d(TAG, "EdgeDetectionGLView initialized");
    }
    
    public void updateFrame(Mat frame) {
        if (mRenderer != null && frame != null) {
            // Convert Mat to byte array
            byte[] imageData = matToByteArray(frame);
            if (imageData != null) {
                mRenderer.updateTexture(imageData, frame.cols(), frame.rows());
            }
        }
    }
    
    private byte[] matToByteArray(Mat mat) {
        try {
            if (mat == null || mat.empty()) {
                Log.w(TAG, "Mat is null or empty");
                return null;
            }
            
            // Ensure the Mat is RGBA format
            Mat rgbaMat = new Mat();
            if (mat.channels() == 1) {
                // Convert grayscale to RGBA
                org.opencv.imgproc.Imgproc.cvtColor(mat, rgbaMat, org.opencv.imgproc.Imgproc.COLOR_GRAY2RGBA);
            } else if (mat.channels() == 3) {
                // Convert BGR to RGBA
                org.opencv.imgproc.Imgproc.cvtColor(mat, rgbaMat, org.opencv.imgproc.Imgproc.COLOR_BGR2RGBA);
            } else if (mat.channels() == 4) {
                // Already RGBA
                rgbaMat = mat.clone();
            } else {
                Log.w(TAG, "Unsupported Mat format with " + mat.channels() + " channels");
                return null;
            }
            
            // Validate dimensions
            if (rgbaMat.cols() <= 0 || rgbaMat.rows() <= 0) {
                Log.w(TAG, "Invalid Mat dimensions: " + rgbaMat.cols() + "x" + rgbaMat.rows());
                return null;
            }
            
            // Convert to byte array
            byte[] data = new byte[(int) (rgbaMat.total() * rgbaMat.elemSize())];
            rgbaMat.get(0, 0, data);
            
            Log.d(TAG, "Converted Mat to byte array: " + rgbaMat.cols() + "x" + rgbaMat.rows() + ", " + data.length + " bytes");
            return data;
        } catch (Exception e) {
            Log.e(TAG, "Error converting Mat to byte array: " + e.getMessage(), e);
            return null;
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "GLView paused");
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "GLView resumed");
    }
    
    public void cleanup() {
        if (mRenderer != null) {
            mRenderer.cleanup();
        }
    }
}
