package com.della_irvianti.eye_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailLatihanActivity extends AppCompatActivity {

    private static final String TAG = "DetailLatihanActivity";

    TextView latihan1_title;
    ImageView latihan1_gambar;
    LinearLayout instruksiContainer, manfaatContainer;

    // Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_latihan);

        latihan1_title = findViewById(R.id.latihan1_title);
        latihan1_gambar = findViewById(R.id.latihan1_gambar);
        instruksiContainer = findViewById(R.id.instruksi_container);
        manfaatContainer = findViewById(R.id.manfaat_container);

        // Referensi ke dokumen Firestore
        DocumentReference docRef = db.collection("latihan").document("palming");

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "Dokumen ditemukan.");
                    // Ambil data dari Firestore
                    String title = document.getString("title");
                    List<String> instruksiList = (List<String>) document.get("instruksi");
                    List<String> manfaatList = (List<String>) document.get("manfaat");
                    String gambarUrl = document.getString("gambar");

                    // Set data ke TextView dan ImageView
                    latihan1_title.setText(title != null ? title : "Judul tidak ditemukan");

                    if (instruksiList != null) {
                        for (String instruksi : instruksiList) {
                            TextView instruksiTextView = new TextView(this);
                            instruksiTextView.setText(instruksi);
                            instruksiContainer.addView(instruksiTextView);
                        }
                    } else {
                        Log.d(TAG, "Daftar instruksi kosong atau tidak ditemukan.");
                    }

                    if (manfaatList != null) {
                        for (String manfaat : manfaatList) {
                            TextView manfaatTextView = new TextView(this);
                            manfaatTextView.setText(manfaat);
                            manfaatContainer.addView(manfaatTextView);
                        }
                    } else {
                        Log.d(TAG, "Daftar manfaat kosong atau tidak ditemukan.");
                    }

                    // Muat gambar menggunakan Picasso
                    if (gambarUrl != null && !gambarUrl.isEmpty()) {
                        Picasso.get().load(gambarUrl).into(latihan1_gambar);
                    } else {
                        Log.d(TAG, "URL gambar kosong atau tidak ditemukan.");
                    }
                } else {
                    Log.d(TAG, "Dokumen tidak ditemukan.");
                    latihan1_title.setText("Dokumen tidak ditemukan");
                }
            } else {
                Log.w(TAG, "Gagal mengambil dokumen: ", task.getException());
                latihan1_title.setText("Gagal mengambil data");
            }
        });
    }
}
