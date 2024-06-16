package com.della_irvianti.eye_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.della_irvianti.eye_app.adapter.ItemListAdapter;
import com.della_irvianti.eye_app.model.Item;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private final ItemListAdapter itemListAdapter = new ItemListAdapter();
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // Inisialisasi Firebase Firestore
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Setup back button
        ImageView ivBack = findViewById(R.id.back_button);
        ivBack.setOnClickListener(v -> finish());

        // Setup RecyclerView
        setupRecyclerView();

        // Fetch data dari Firestore
        fetchData();
    }

    // Method untuk fetch data dari Firestore
    private void fetchData() {
        // Menampilkan loading
        showLoading(true);

        // Fetch data dari Firestore
        firebaseFirestore.collection("makanan-sehat")
                .get()
                .addOnCompleteListener(taskFetchData -> {
                    // Menyembunyikan loading
                    showLoading(false);

                    // Jika fetch data berhasil
                    if (taskFetchData.isSuccessful()) {

                        // Mengubah data Firestore ke List<Item>
                        List<Item> items = taskFetchData.getResult().toObjects(Item.class);

                        // Set data ke adapter
                        itemListAdapter.setListItem(items);
                    }
                });
    }

    // Method untuk menampilkan loading
    private void showLoading(Boolean showLoading) {
        CircularProgressIndicator progressIndicator = findViewById(R.id.progress_bar);

        // Menampilkan atau menyembunyikan loading
        progressIndicator.setVisibility(showLoading ? View.VISIBLE : View.GONE);
    }

    // Method untuk setup RecyclerView
    private void setupRecyclerView() {
        // Inisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_list);

        // Set adapter ke RecyclerView
        recyclerView.setAdapter(itemListAdapter);

        // Set RecyclerView agar memiliki ukuran yang tetap
        recyclerView.setHasFixedSize(true);

        // Set layout manager ke RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}