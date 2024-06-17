package com.della_irvianti.eye_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.della_irvianti.eye_app.adapter.LatihanListAdapter;
import com.della_irvianti.eye_app.model.Latihan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class LatihanActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private LatihanListAdapter latihanListAdapter = new LatihanListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan);

        firebaseFirestore = FirebaseFirestore.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.latihanActivity);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.homeActivity) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.latihanActivity) {
                return true;
            } else if (id == R.id.gameActivity) {
                startActivity(new Intent(getApplicationContext(), MenuGameActivity.class));
                    overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        setupRecyclerView();
        fetchData();
    }

    private void fetchData() {
        // Menampilkan ProgressBar saat data sedang dimuat
        showLoading(true);

        // Mengambil data latihan dari Firestore
        firebaseFirestore.collection("latihan")
                .get()
                .addOnCompleteListener(task -> {
                    // Menyembunyikan ProgressBar saat data sudah dimuat
                    showLoading(false);

                    // Menampilkan data latihan jika berhasil dimuat
                    if (task.isSuccessful()) {
                        // Mengambil data latihan dari Firestore
                        List<Latihan> listLatihan = task.getResult().toObjects(Latihan.class);

                        // Menetapkan data latihan ke adapter RecyclerView
                        latihanListAdapter.setListLatihan(listLatihan);
                    } else {
                        // Menampilkan pesan error
                        Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Menampilkan ProgressBar saat data sedang dimuat
    private void showLoading(boolean showLoading) {
        if (showLoading) {
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView() {
        // Menginisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_latihan);

        // Mengatur ukuran RecyclerView agar fleksibel
        recyclerView.setHasFixedSize(false);

        // Mengatur layout RecyclerView menjadi Grid dengan 2 kolom
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        // Mengatur adapter RecyclerView
        recyclerView.setAdapter(latihanListAdapter);

        // Menangani klik pada item RecyclerView
        latihanListAdapter.setOnItemClickListener(latihan -> {
            // Buat Intent untuk memulai DetailLatihanActivity
            Intent intent = new Intent(LatihanActivity.this, DetailLatihanActivity.class);

            // Mengirim data id dokumen latihan melalui Intent
            intent.putExtra(DetailLatihanActivity.EXTRA_LATIHAN_ID, latihan.getId());

            // Memulai DetailLatihanActivity
            startActivity(intent);
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