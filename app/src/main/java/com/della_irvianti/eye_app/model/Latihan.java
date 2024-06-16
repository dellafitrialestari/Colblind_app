package com.della_irvianti.eye_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Latihan implements Parcelable {
    @DocumentId
    private String id;

    private String title, gambar, icon;

    @PropertyName("ins-deskripsi")
    private String insDeskripsi;

    private List<String> manfaat, instruksi;

    public Latihan() {}

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @PropertyName("ins-deskripsi")
    public String getInsDeskripsi() {
        return insDeskripsi;
    }

    @PropertyName("ins-deskripsi")
    public void setInsDeskripsi(String insDeskripsi) {
        this.insDeskripsi = insDeskripsi;
    }

    public List<String> getManfaat() {
        return manfaat;
    }

    public void setManfaat(List<String> manfaat) {
        this.manfaat = manfaat;
    }

    public List<String> getInstruksi() {
        return instruksi;
    }

    public void setInstruksi(List<String> instruksi) {
        this.instruksi = instruksi;
    }

    @DocumentId
    public String getId() {
        return id;
    }

    @DocumentId
    public void setId(String id) {
        this.id = id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(title);
        out.writeString(gambar);
        out.writeString(icon);
        out.writeString(insDeskripsi);
        out.writeStringList(manfaat);
        out.writeStringList(instruksi);
    }

    public static final Creator<Latihan> CREATOR
            = new Creator<Latihan>() {
        public Latihan createFromParcel(Parcel in) {
            return new Latihan(in);
        }

        public Latihan[] newArray(int size) {
            return new Latihan[size];
        }
    };

    private Latihan(Parcel in) {
        id = in.readString();
        title = in.readString();
        gambar = in.readString();
        icon = in.readString();
        insDeskripsi = in.readString();
        manfaat = in.createStringArrayList();
        instruksi = in.createStringArrayList();
    }
}
