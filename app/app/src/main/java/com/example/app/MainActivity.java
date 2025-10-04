package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button edgeBtn = findViewById(R.id.edgeDetectionBtn);
        Button colorBtn = findViewById(R.id.colorSegmentationBtn);

        edgeBtn.setOnClickListener(v -> openCamera("edge"));
        colorBtn.setOnClickListener(v -> openCamera("color"));
    }

    private void openCamera(String mode) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }
}
