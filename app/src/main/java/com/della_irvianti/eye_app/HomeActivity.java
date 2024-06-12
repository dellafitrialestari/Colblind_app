package com.della_irvianti.eye_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.della_irvianti.eye_app.Test.TestActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Mengatur BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeFragment) {
                    return true;
                } else if (id == R.id.latihanFragment) {
                    startActivity(new Intent(getApplicationContext(), LatihanActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.gameFragment) {
                    startActivity(new Intent(getApplicationContext(), MenuGameActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        // Mengatur onClickListeners untuk LinearLayouts
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
                // Buat Intent untuk memulai InstruksiActivity
                Intent intent = new Intent(HomeActivity.this, InstruksiActivity.class);
                startActivity(intent);
            }
        });
    }
}
