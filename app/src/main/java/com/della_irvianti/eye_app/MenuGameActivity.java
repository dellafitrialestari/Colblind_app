package com.della_irvianti.eye_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MenuGameActivity extends AppCompatActivity {
    public static final String PREFS_DATA_Difficulty = "Difficulty_PREFS";
    public static final String PREFS_DATA_Settings = "Settings_PREFS";

    private String diff_status;
    private String vibrate_status;
    private String sound_status;
    private String moving_status;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game);

        // Menghilangkan ActionBar dan membuat tampilan fullscreen
//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Mengambil SharedPreferences untuk pengaturan
        SharedPreferences shared_pref = getSharedPreferences(PREFS_DATA_Difficulty, MODE_PRIVATE);
        diff_status = shared_pref.getString("level_difficulty", "0");
        SharedPreferences settings_pref = getSharedPreferences(PREFS_DATA_Settings, MODE_PRIVATE);
        vibrate_status = settings_pref.getString("vibrate_status", "1");
        sound_status = settings_pref.getString("sound_status", "0");
        moving_status = settings_pref.getString("moving_status", "1");

        // MediaPlayer untuk suara
        mediaPlayer = MediaPlayer.create(this, R.raw.correct);
        MediaPlayer.OnCompletionListener onCompletionListener = mp -> releaseMediaPlayerResources();
        mediaPlayer.setOnCompletionListener(onCompletionListener);

        // Mengatur TabLayout untuk tingkat kesulitan
        TabLayout tabLayout = findViewById(R.id.diff_tab);
        TabLayout.Tab tab = tabLayout.getTabAt(Integer.parseInt(diff_status));
        if (tab != null) tab.select();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                diff_status = String.valueOf(tab.getPosition());
                SharedPreferences.Editor editor = shared_pref.edit();
                editor.putString("level_difficulty", diff_status);
                editor.apply();

                if (Integer.parseInt(sound_status) == 1) {
                    mediaPlayer.start();
                }
                if (Integer.parseInt(vibrate_status) == 1) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        v.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                    } else {
                        v.vibrate(10);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                diff_status = "0";
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Tombol untuk memulai permainan
        Button play_btn = findViewById(R.id.playbtn);
        play_btn.setOnClickListener(view -> {
            if (Integer.parseInt(sound_status) == 1) {
                mediaPlayer.start();
            }
            if (Integer.parseInt(vibrate_status) == 1) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    v.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                } else {
                    v.vibrate(10);
                }
            }
            Intent intent = new Intent(MenuGameActivity.this, GameActivity.class);
            intent.putExtra("diff_status", diff_status);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        // Tombol untuk membuka pengaturan
        Button settings_btn = findViewById(R.id.setting);
        settings_btn.setOnClickListener(view -> {
            if (Integer.parseInt(sound_status) == 1) {
                mediaPlayer.start();
            }
            if (Integer.parseInt(vibrate_status) == 1) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    v.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
                } else {
                    v.vibrate(10);
                }
            }
            Intent intent_setting = new Intent(MenuGameActivity.this, SettingsActivity.class);
            startActivity(intent_setting);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        // Mengatur BottomNavigationView untuk navigasi
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.gameFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.homeFragment) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.latihanFragment) {
                startActivity(new Intent(getApplicationContext(), LatihanActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.gameFragment) {
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onStop() {
        releaseMediaPlayerResources();
        super.onStop();
    }

    @Override
    protected void onResume() {
        mediaPlayer = MediaPlayer.create(this, R.raw.correct);
        super.onResume();
    }

    private void releaseMediaPlayerResources() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
