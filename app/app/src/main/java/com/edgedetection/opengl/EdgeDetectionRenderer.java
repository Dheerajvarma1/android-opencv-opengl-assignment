package com.edgedetection.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EdgeDetectionRenderer implements GLSurfaceView.Renderer {
    
    private static final String TAG = "EdgeDetectionRenderer";
    
    // Shader program
    private int mProgram;
    
    // Attribute locations
    private int mPositionHandle;
    private int mTexCoordHandle;
    
    // Uniform locations
    private int mTextureHandle;
    private int mMVPMatrixHandle;
    
    // Matrices
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    
    // Vertex data for a full-screen quad
    private static final float[] VERTEX_DATA = {
        // X, Y, Z, U, V
        -1.0f, -1.0f, 0.0f, 0.0f, 1.0f,  // Bottom left
         1.0f, -1.0f, 0.0f, 1.0f, 1.0f,  // Bottom right
        -1.0f,  1.0f, 0.0f, 0.0f, 0.0f,  // Top left
         1.0f,  1.0f, 0.0f, 1.0f, 0.0f   // Top right
    };
    
    private FloatBuffer mVertexBuffer;
    
    // Texture
    private int mTextureId;
    private int mTextureWidth = 0;
    private int mTextureHeight = 0;
    
    // Context for loading shaders
    private Context mContext;
    
    public EdgeDetectionRenderer(Context context) {
        mContext = context;
        
        // Initialize vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(VERTEX_DATA.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(VERTEX_DATA);
        mVertexBuffer.position(0);
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "Surface created");
        
        // Set background color to black
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        // Enable blending for transparency
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        
        // Load and compile shaders
        loadShaders();
        
        // Generate texture
        generateTexture();
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "Surface changed: " + width + "x" + height);
        
        GLES20.glViewport(0, 0, width, height);
        
        // Set up projection matrix
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        
        // Set up view matrix
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        
        // Calculate MVP matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the screen
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        
        // Use the shader program
        GLES20.glUseProgram(mProgram);
        
        // Set MVP matrix
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        
        // Set vertex attributes
        mVertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 5 * 4, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        
        mVertexBuffer.position(3);
        GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 5 * 4, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordHandle);
        
        // Bind texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glUniform1i(mTextureHandle, 0);
        
        // Draw the quad
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        
        // Disable vertex arrays
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordHandle);
    }
    
    private void loadShaders() {
        // Load vertex shader
        String vertexShaderCode = loadShaderFromAssets("shaders/vertex_shader.glsl");
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        
        // Load fragment shader
        String fragmentShaderCode = loadShaderFromAssets("shaders/fragment_shader.glsl");
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        
        // Create program
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        
        // Check linking status
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            String error = GLES20.glGetProgramInfoLog(mProgram);
            Log.e(TAG, "Shader program linking failed: " + error);
            GLES20.glDeleteProgram(mProgram);
            mProgram = 0;
            return;
        }
        
        // Get attribute and uniform locations
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoord");
        mTextureHandle = GLES20.glGetUniformLocation(mProgram, "uTexture");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        
        Log.d(TAG, "Shaders loaded successfully");
    }
    
    private String loadShaderFromAssets(String filename) {
        try {
            java.io.InputStream inputStream = mContext.getAssets().open(filename);
            java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
            String shaderCode = scanner.hasNext() ? scanner.next() : "";
            scanner.close();
            inputStream.close();
            return shaderCode;
        } catch (Exception e) {
            Log.e(TAG, "Error loading shader from assets: " + e.getMessage());
            return "";
        }
    }
    
    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        
        // Check compilation status
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] != GLES20.GL_TRUE) {
            String error = GLES20.glGetShaderInfoLog(shader);
            Log.e(TAG, "Shader compilation failed: " + error);
            GLES20.glDeleteShader(shader);
            return 0;
        }
        
        return shader;
    }
    
    private void generateTexture() {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        mTextureId = textures[0];
        
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        
        // Set texture parameters
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        
        Log.d(TAG, "Texture generated with ID: " + mTextureId);
    }
    
    public void updateTexture(byte[] imageData, int width, int height) {
        if (mTextureId == 0) {
            Log.w(TAG, "Texture not initialized");
            return;
        }
        
        if (imageData == null || imageData.length == 0) {
            Log.w(TAG, "Invalid image data");
            return;
        }
        
        if (width <= 0 || height <= 0) {
            Log.w(TAG, "Invalid texture dimensions: " + width + "x" + height);
            return;
        }
        
        // Update texture dimensions if changed
        if (mTextureWidth != width || mTextureHeight != height) {
            mTextureWidth = width;
            mTextureHeight = height;
            Log.d(TAG, "Updating texture size: " + width + "x" + height);
        }
        
        try {
            // Bind texture and upload data
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ByteBuffer.wrap(imageData));
            
            // Check for OpenGL errors
            int error = GLES20.glGetError();
            if (error != GLES20.GL_NO_ERROR) {
                Log.e(TAG, "OpenGL error in updateTexture: " + error);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating texture: " + e.getMessage(), e);
        }
    }
    
    public void cleanup() {
        if (mTextureId != 0) {
            GLES20.glDeleteTextures(1, new int[]{mTextureId}, 0);
            mTextureId = 0;
        }
        if (mProgram != 0) {
            GLES20.glDeleteProgram(mProgram);
            mProgram = 0;
        }
    }
}
