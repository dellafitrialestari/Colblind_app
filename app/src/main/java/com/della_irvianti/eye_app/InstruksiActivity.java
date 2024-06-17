package com.della_irvianti.eye_app;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class InstruksiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruksi);

        // Setup back button
        ImageView ivBack = findViewById(R.id.back_button);
        ivBack.setOnClickListener(v -> finish());

    }
}