package com.example.moda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moda.R;
import com.example.moda.models.Marca;

import java.util.ArrayList;
import java.util.List;

public class MarcaAdapter extends RecyclerView.Adapter<MarcaAdapter.ViewHolder> {
    private List<Marca> marcas = new ArrayList<>();

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Marca marca = marcas.get(position);
        
        holder.tvNombreMarca.setText(marca.getNombre());
        holder.tvRating.setText("★ " + marca.getRating());



        String imageUrl = marca.getImagenFondoUrl();
        if (imageUrl != null) {
            imageUrl = imageUrl.trim();
            if (imageUrl.contains("127.0.0.1")) {
                imageUrl = imageUrl.replace("127.0.0.1", "10.0.2.2");
            } else if (imageUrl.contains("localhost")) {
                imageUrl = imageUrl.replace("localhost", "10.0.2.2");
            }
        }

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        return marcas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvNombreMarca, tvRating;

        ViewHolder(View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.ivLogoMarca);
            tvNombreMarca = itemView.findViewById(R.id.tvNombreMarca);
            tvRating = itemView.findViewById(R.id.tvRatingMarca);
        }
    }
}
