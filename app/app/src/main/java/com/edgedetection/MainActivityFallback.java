package com.edgedetection;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;

public class MainActivityFallback extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "EdgeDetectionFallback";
    private static final int CAMERA_PERMISSION_REQUEST = 1;
    
    // Load OpenCV library statically
    static {
        try {
            System.loadLibrary("opencv_java4");
            Log.i("EdgeDetection", "OpenCV library loaded successfully!");
        } catch (UnsatisfiedLinkError e) {
            Log.e("EdgeDetection", "Failed to load OpenCV library: " + e.getMessage());
        }
    }

    private JavaCameraView cameraView;
    private SeekBar lowerThresholdBar;
    private SeekBar upperThresholdBar;
    private SeekBar blurBar;
    private TextView lowerThresholdText;
    private TextView upperThresholdText;
    private TextView blurText;
    private TextView fpsText;

    private Mat rgba;
    private Mat edges;

    private int lowerThreshold = 50;
    private int upperThreshold = 150;
    private int blurValue = 5;

    private long lastFrameTime = 0;
    private double fps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        setContentView(R.layout.activity_main_fallback);

        // Initialize views
        cameraView = findViewById(R.id.camera_view);
        lowerThresholdBar = findViewById(R.id.lower_threshold_bar);
        upperThresholdBar = findViewById(R.id.upper_threshold_bar);
        blurBar = findViewById(R.id.blur_bar);
        lowerThresholdText = findViewById(R.id.lower_threshold_text);
        upperThresholdText = findViewById(R.id.upper_threshold_text);
        blurText = findViewById(R.id.blur_text);
        fpsText = findViewById(R.id.fps_text);

        // Set up camera view
        cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.setCvCameraViewListener(this);

        // Set up seekbars
        setupSeekBars();

        // Request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) 
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, 
                    new String[]{Manifest.permission.CAMERA}, 
                    CAMERA_PERMISSION_REQUEST);
        } else {
            cameraView.setCameraPermissionGranted();
        }

        // Check if native library loaded
        if (!EdgeDetector.isLibraryLoaded()) {
            Toast.makeText(this, "Failed to load native library! Check Logcat.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Native library not loaded - edge detection will not work!");
        }

        Log.i(TAG, "Edge Detection App Started (Fallback Mode)!");
    }

    private void setupSeekBars() {
        // Lower Threshold SeekBar
        lowerThresholdBar.setMax(255);
        lowerThresholdBar.setProgress(lowerThreshold);
        lowerThresholdBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lowerThreshold = progress;
                lowerThresholdText.setText("Lower: " + lowerThreshold);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Upper Threshold SeekBar
        upperThresholdBar.setMax(255);
        upperThresholdBar.setProgress(upperThreshold);
        upperThresholdBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                upperThreshold = progress;
                upperThresholdText.setText("Upper: " + upperThreshold);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Blur SeekBar
        blurBar.setMax(15);
        blurBar.setProgress(blurValue);
        blurBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blurValue = Math.max(1, progress);
                blurText.setText("Blur: " + blurValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraView.setCameraPermissionGranted();
            } else {
                Toast.makeText(this, "Camera permission required!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.d(TAG, "Camera view started: " + width + "x" + height);
        rgba = new Mat();
        edges = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        Log.d(TAG, "Camera view stopped");
        if (rgba != null) {
            rgba.release();
        }
        if (edges != null) {
            edges.release();
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        try {
            rgba = inputFrame.rgba();

            // Calculate FPS
            long currentTime = System.currentTimeMillis();
            if (lastFrameTime != 0) {
                fps = 1000.0 / (currentTime - lastFrameTime);
            }
            lastFrameTime = currentTime;

            // Update FPS on UI thread
            runOnUiThread(() -> fpsText.setText(String.format("FPS: %.1f", fps)));

            // Call C++ edge detection if library is loaded
            if (EdgeDetector.isLibraryLoaded()) {
                EdgeDetector.detectEdges(
                    rgba.getNativeObjAddr(),
                    edges.getNativeObjAddr(),
                    lowerThreshold,
                    upperThreshold,
                    blurValue
                );
                return edges;
            } else {
                // If native library not loaded, just return original frame
                return rgba;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing frame: " + e.getMessage(), e);
            return rgba; // Return original frame if processing fails
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cameraView != null) {
            cameraView.enableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraView != null) {
            cameraView.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraView != null) {
            cameraView.disableView();
        }
    }
}

