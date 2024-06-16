package com.della_irvianti.eye_app.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.della_irvianti.eye_app.R;
import com.della_irvianti.eye_app.model.Latihan;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class LatihanListAdapter extends RecyclerView.Adapter<LatihanListAdapter.ViewHolder> {

    // Data yang akan ditampilkan di RecyclerView
    private List<Latihan> listLatihan = Collections.emptyList();

    // Interface untuk item click
    public interface OnItemClickListener {
        void onItemClick(Latihan item);
    }

    // Listener untuk item click
    private static OnItemClickListener listener;

    // Setter untuk listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        LatihanListAdapter.listener = listener;
    }

    // ViewHolder untuk RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final ImageView ivIcon;
        private final View view;

        public ViewHolder(View view) {
            super(view);
            textTitle = view.findViewById(R.id.text_title);
            ivIcon = view.findViewById(R.id.iv_icon);
            this.view = view;
        }

        public TextView getTextTitle() {
            return textTitle;
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

        public View getView() {
            return view;
        }
    }

    public LatihanListAdapter() {}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Membuat view baru
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_latihan, viewGroup, false);

        // Mengembalikan ViewHolder baru
        return new ViewHolder(view);
    }

    // Menghubungkan data dengan ViewHolder pada position yang ditentukan
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Mengambil elemen dari dataset pada position
        Latihan dataLatihan = listLatihan.get(position);

        // Mengisi data ke view
        viewHolder.getTextTitle().setText(dataLatihan.getTitle());

        // Membuat CircularProgressDrawable
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(viewHolder.getView().getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        // Muat gambar menggunakan Coil
        ImageLoader imageLoader = Coil.imageLoader(viewHolder.getView().getContext());
        ImageRequest request = new ImageRequest.Builder(viewHolder.getView().getContext())
                .placeholder(circularProgressDrawable)
                .data(dataLatihan.getIcon())
                .crossfade(true)
                .target(viewHolder.getIvIcon())
                .build();
        imageLoader.enqueue(request);
        Picasso.get().load(dataLatihan.getIcon()).into(viewHolder.getIvIcon());

        viewHolder.getView().setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(dataLatihan);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListLatihan(List<Latihan> listLatihan) {
        this.listLatihan = listLatihan;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listLatihan.size();
    }
}

