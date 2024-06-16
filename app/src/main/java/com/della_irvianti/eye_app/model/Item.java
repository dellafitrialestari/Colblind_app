package com.della_irvianti.eye_app.model;

import com.google.firebase.firestore.DocumentId;

public class Item {
    String title, gambar, deskripsi;

    @DocumentId
    String id;

    public Item() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @DocumentId
    public String getId() {
        return id;
    }

    @DocumentId
    public void setId(String id) {
        this.id = id;
    }
}
