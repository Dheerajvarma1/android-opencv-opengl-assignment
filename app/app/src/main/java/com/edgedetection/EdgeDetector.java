package com.edgedetection;

import android.util.Log;

public class EdgeDetector {
    
    private static final String TAG = "EdgeDetector";
    private static boolean libraryLoaded = false;

    // Load native library
    static {
        try {
            System.loadLibrary("edge_detection");
            libraryLoaded = true;
            Log.i(TAG, "Native edge_detection library loaded successfully!");
        } catch (UnsatisfiedLinkError e) {
            libraryLoaded = false;
            Log.e(TAG, "Failed to load native library: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static boolean isLibraryLoaded() {
        return libraryLoaded;
    }

    /**
     * Detect edges in the input image using Canny edge detection
     * 
     * @param inputAddr  Native address of input Mat (RGBA)
     * @param outputAddr Native address of output Mat (edges)
     * @param lowerThreshold Lower threshold for Canny algorithm
     * @param upperThreshold Upper threshold for Canny algorithm
     * @param blurSize Gaussian blur kernel size
     */
    public static native void detectEdges(
        long inputAddr, 
        long outputAddr,
        int lowerThreshold,
        int upperThreshold,
        int blurSize
    );
}

