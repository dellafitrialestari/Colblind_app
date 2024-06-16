package com.della_irvianti.eye_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.della_irvianti.eye_app.model.Latihan;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class DetailLatihanActivity extends AppCompatActivity {

    public static final String EXTRA_LATIHAN_ID = "extra_latihan_id";
    private static final String TAG = "DetailLatihanActivity";
    CircularProgressIndicator progressIndicator;
    TextView latihanTitle, instruksiTitle, manfaatTitle;
    LinearLayout instruksiContainer, manfaatContainer;
    ImageView latihanGambar, backButton;

    // Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_latihan);

        latihanTitle = findViewById(R.id.latihan_title);
        latihanGambar = findViewById(R.id.latihan_gambar);
        backButton = findViewById(R.id.back_button);
        instruksiContainer = findViewById(R.id.instruksi_container);
        manfaatContainer = findViewById(R.id.manfaat_container);
        progressIndicator = findViewById(R.id.progress_bar);
        instruksiTitle = findViewById(R.id.instruksi_title);
        manfaatTitle = findViewById(R.id.manfaat_title);

        // Ambil ID dokumen dari Intent
        String idDokumen = getIntent().getStringExtra(EXTRA_LATIHAN_ID);

        // Jika ID dokumen kosong, tampilkan pesan dan selesaikan Activity
        if (idDokumen == null || idDokumen.isEmpty()) {
            Toast.makeText(this, "ID dokumen tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        backButton.setOnClickListener(v -> finish());
        showLoading(true);

        // Referensi ke dokumen Firestore
        DocumentReference docRef = db.collection("latihan").document(idDokumen);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                showLoading(false);
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "Dokumen ditemukan.");
                    // Ambil data dari Firestore
                    Latihan latihan = document.toObject(Latihan.class);

                    // Set data ke TextView dan ImageView
                    latihanTitle.setText(latihan.getTitle() != null ? latihan.getTitle() : "Judul tidak ditemukan");

                    if (latihan.getInstruksi() != null) {
                        int instructionNumber = 1;
                        for (String instruksi : latihan.getInstruksi()) {
                            LinearLayout instructionLayout = new LinearLayout(this);
                            instructionLayout.setOrientation(LinearLayout.HORIZONTAL);
                            instructionLayout.setPadding(20, 10, 20, 10); // Optional padding

                            TextView numberTextView = new TextView(this);
                            numberTextView.setText(instructionNumber + ".");
                            numberTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));

                            TextView instruksiTextView = new TextView(this);
                            instruksiTextView.setText(instruksi);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(10, 0, 0, 0);
                            instruksiTextView.setLayoutParams(params);

                            instructionLayout.addView(numberTextView);
                            instructionLayout.addView(instruksiTextView);

                            instruksiContainer.addView(instructionLayout);

                            instructionNumber++;
                        }
                    } else {
                        Log.d(TAG, "Daftar instruksi kosong atau tidak ditemukan.");
                    }

                    if (latihan.getManfaat() != null) {
                        int manfaatNumber = 1;
                        for (String manfaat : latihan.getManfaat()) {
                            LinearLayout manfaatLayout = new LinearLayout(this);
                            manfaatLayout.setOrientation(LinearLayout.HORIZONTAL);
                            manfaatLayout.setPadding(20, 10, 20, 10); // Optional padding

                            TextView numberTextView = new TextView(this);
                            numberTextView.setText(manfaatNumber + ".");
                            numberTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));

                            TextView manfaatTextView = new TextView(this);
                            manfaatTextView.setText(manfaat);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(10, 0, 0, 0); // Set margin to create indent
                            manfaatTextView.setLayoutParams(params);

                            manfaatLayout.addView(numberTextView);
                            manfaatLayout.addView(manfaatTextView);

                            manfaatContainer.addView(manfaatLayout);

                            manfaatNumber++;
                        }
                    } else {
                        Log.d(TAG, "Daftar manfaat kosong atau tidak ditemukan.");
                    }

                    // Muat gambar menggunakan Coil
                    if (latihan.getGambar() != null && !latihan.getGambar().isEmpty()) {
                        // Tampilkan CircularProgressDrawable saat gambar sedang dimuat
                        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
                        circularProgressDrawable.setStrokeWidth(5f);
                        circularProgressDrawable.setColorSchemeColors(getResources().getColor(R.color.white));
                        circularProgressDrawable.setCenterRadius(30f);
                        circularProgressDrawable.start();

                        // Muat gambar menggunakan Coil
                        ImageLoader imageLoader = Coil.imageLoader(this);
                        ImageRequest request = new ImageRequest.Builder(this)
                                .placeholder(circularProgressDrawable)
                                .data(latihan.getGambar())
                                .crossfade(true)
                                .target(latihanGambar)
                                .build();
                        imageLoader.enqueue(request);
                    } else {
                        Log.d(TAG, "URL gambar kosong atau tidak ditemukan.");
                    }
                } else {
                    Log.d(TAG, "Dokumen tidak ditemukan.");
                    latihanTitle.setText("Dokumen tidak ditemukan");
                }
            } else {
                Log.w(TAG, "Gagal mengambil dokumen: ", task.getException());
                latihanTitle.setText("Gagal mengambil data");
            }
        });
    }

    private void showLoading(Boolean showLoading) {
        if (showLoading) {
            progressIndicator.setVisibility(View.VISIBLE);
            latihanGambar.setVisibility(View.GONE);
            instruksiTitle.setVisibility(View.GONE);
            manfaatTitle.setVisibility(View.GONE);
        } else {
            progressIndicator.setVisibility(View.GONE);
            latihanGambar.setVisibility(View.VISIBLE);
            instruksiTitle.setVisibility(View.VISIBLE);
            manfaatTitle.setVisibility(View.VISIBLE);
        }
    }
}
