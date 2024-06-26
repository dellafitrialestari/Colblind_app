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
        bottomNavigationView.setSelectedItemId(R.id.homeActivity);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeActivity) {
                    return true;
                } else if (id == R.id.latihanActivity) {
                    startActivity(new Intent(getApplicationContext(), LatihanActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.gameActivity) {
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
        LinearLayout tips = findViewById(R.id.tips);
        LinearLayout food = findViewById(R.id.makanan_sehat);

        test_butawarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TestActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
            }
        });

        instruksi_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InstruksiActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TipsMataActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Mengakhiri aplikasi ketika tombol back ditekan
        super.onBackPressed();
        finishAffinity();
        overridePendingTransition(0, 0);
    }
}
