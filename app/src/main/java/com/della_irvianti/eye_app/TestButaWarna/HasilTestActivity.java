package com.della_irvianti.eye_app.TestButaWarna;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.della_irvianti.eye_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HasilTestActivity extends Activity {
    private int soal;
    LinearLayout selanjutnya,kembali,sebelumnya,cobalagi;
    TextView isi,isi2,isi3,vbenar,hasil;
    ImageView iv_flag;
    List<ItemTest> list,list2,list3;
    Random r;
    int turn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_test);
        Toast.makeText(HasilTestActivity.this, "Anda Telah menyelesaikan soal", Toast.LENGTH_SHORT).show();
        r = new Random() ;
        iv_flag = (ImageView) findViewById(R.id.iv_flag);

        isi = (TextView)findViewById(R.id.isi);
        isi2 = (TextView)findViewById(R.id.isi2);
        isi3 = (TextView)findViewById(R.id.isi3);
        vbenar = (TextView) findViewById(R.id.vbenar);
        hasil = (TextView)findViewById(R.id.hasil);

        selanjutnya= (LinearLayout) findViewById(R.id.selanjutnya);
        sebelumnya= (LinearLayout) findViewById(R.id.sebelumnya);
        cobalagi = (LinearLayout) findViewById(R.id.cobalagi);
//        kembali = (LinearLayout) findViewById(R.id.kembali);

        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        //Isi semua Gambar dan Hasil jawaban
        for(int i = 0; i< new DatabaseTest().answers.length; i++){
            list.add(new ItemTest(new DatabaseTest().penjelasan[i],new DatabaseTest().flags[i]));
            list2.add(new ItemTest(new DatabaseTest().penjelasan2[i],new DatabaseTest().flags[i]));
            list3.add(new ItemTest(new DatabaseTest().penjelasan3[i],new DatabaseTest().flags[i]));
        }

        //Menampilkan Skor
        soal = list.size();
        int benar = getIntent().getIntExtra("Benar",0);
        vbenar.setText("Jumlah Benar : "+benar+"/"+soal);

        if (benar < 3){
            hasil.setText("Anda buta warna total");
        }
        else if(benar < 8){
            hasil.setText("Anda buta warna sebagian");
        }
        else{
            hasil.setText("Mata anda normal");
        }
        newQuestion(turn);

        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn<list.size()){
                    turn++;
                    newQuestion(turn);
                }else{
                    Toast.makeText(HasilTestActivity.this, "Ini Penjelasan Terakhir", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        kembali.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(HasilTestActivity.this);
//                builder.setMessage("Anda yakin ingin kembali ke Menu ?")
//                        .setPositiveButton("Tidak",null)
//                        .setNegativeButton("Ya", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        });

        sebelumnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn == 1){
                    Toast.makeText(HasilTestActivity.this, "Ini Penjelasan Pertama", Toast.LENGTH_SHORT).show();
                }else{
                    turn--;
                    newQuestion(turn);
                }
            }
        });

        cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HasilTestActivity.this);
                builder.setMessage("Anda ingin mengulang kembali ?")
                        .setPositiveButton("Tidak",null)
                        .setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cobalagi();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void cobalagi(){
        finish();
        Intent intent2 = new Intent(this, TestActivity.class);
        startActivity(intent2);
    }
    private void newQuestion(int number) {
        //Isi gambar dan penjelasan ke dalam screen
        int satu = number - 1;
        iv_flag.setImageResource(list.get(satu).getImage());
        isi.setText(list.get(satu).getName());
        isi2.setText(list2.get(satu).getName());
        isi3.setText(list3.get(satu).getName());
    }
}