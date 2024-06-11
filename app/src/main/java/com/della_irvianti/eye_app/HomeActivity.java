package com.della_irvianti.eye_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.della_irvianti.eye_app.TestButaWarna.TestActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout test_butawarna = findViewById(R.id.fitur1);
        LinearLayout instruksi_test = findViewById(R.id.fitur2);
        test_butawarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Intent untuk memulai TestActivity
                Intent intent = new Intent(HomeActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
        instruksi_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Intent untuk memulai TestActivity
                Intent intent = new Intent(HomeActivity.this, InstruksiActivity.class);
                startActivity(intent);
            }
        });
    }
}