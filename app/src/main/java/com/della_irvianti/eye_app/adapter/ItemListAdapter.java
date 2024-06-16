package com.della_irvianti.eye_app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.della_irvianti.eye_app.R;
import com.della_irvianti.eye_app.model.Item;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<Item> listItem = Collections.emptyList();

    public ItemListAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Item item = listItem.get(position);

        viewHolder.getTvTitle().setText(item.getTitle());
        viewHolder.getTvDescription().setText(item.getDeskripsi());

        // Membuat CircularProgressDrawable sebagai placeholder
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(viewHolder.itemView.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setColorSchemeColors(Color.BLACK);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        // Load gambar dengan Picasso dan gunakan CircularProgressDrawable sebagai placeholder
        Picasso.get()
                .load(item.getGambar())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.error_image) // Anda bisa mengganti dengan gambar error yang sesuai
                .into(viewHolder.getImageView(), new Callback() {
                    @Override
                    public void onSuccess() {
                        // Gambar berhasil dimuat, tidak perlu melakukan apa-apa
                    }

                    @Override
                    public void onError(Exception e) {
                        // Jika ada error saat memuat gambar, bisa melakukan tindakan di sini
                    }
                });
    }

    public void setListItem(List<Item> listLatihan) {
        this.listItem = listLatihan;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvDescription;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDescription = view.findViewById(R.id.tv_description);
            imageView = view.findViewById(R.id.image_view);
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvDescription() {
            return tvDescription;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
