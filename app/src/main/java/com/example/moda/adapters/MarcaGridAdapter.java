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

public class MarcaGridAdapter extends RecyclerView.Adapter<MarcaGridAdapter.ViewHolder> {

    private List<Marca> marcasAll = new ArrayList<>();
    private List<Marca> marcasFiltradas = new ArrayList<>();
    private OnMarcaClickListener listener;

    // Color palette for brand initials boxes
    private static final int[] COLORES = {
        0xFF4C9BE8, // Blue
        0xFF54C5A0, // Teal
        0xFFE87B4C, // Orange
        0xFF8E6BBF, // Purple
        0xFFE8C34C, // Yellow
        0xFF4CB8E8, // Light Blue
        0xFF9BBF4C, // Olive green
        0xFFE84C4C, // Red
    };

    public interface OnMarcaClickListener {
        void onMarcaClick(Marca marca);
    }

    public void setMarcas(List<Marca> marcas, OnMarcaClickListener listener) {
        this.marcasAll = marcas;
        this.marcasFiltradas = new ArrayList<>(marcas);
        this.listener = listener;
        notifyDataSetChanged();
    }

    // Call this AFTER setMarcas to filter the already-loaded list
    public void filtrar(String query) {
        marcasFiltradas = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            marcasFiltradas.addAll(marcasAll);
        } else {
            String q = query.toLowerCase().trim();
            for (Marca m : marcasAll) {
                if (m.getNombre().toLowerCase().contains(q)) {
                    marcasFiltradas.add(m);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marca_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Marca marca = marcasFiltradas.get(position);

        // Load real brand image (same as home menu)
        String imageUrl = marca.getImagenFondoUrl();
        if (imageUrl != null) {
            imageUrl = imageUrl.trim()
                    .replace("127.0.0.1", "10.0.2.2")
                    .replace("localhost", "10.0.2.2");
        }
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivLogoMarca);

        holder.tvNombreMarca.setText(marca.getNombre());
        holder.tvRating.setText("★ " + marca.getRating());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMarcaClick(marca);
        });
    }

    @Override
    public int getItemCount() {
        return marcasFiltradas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLogoMarca;
        TextView tvNombreMarca, tvRating;

        ViewHolder(View itemView) {
            super(itemView);
            ivLogoMarca = itemView.findViewById(R.id.ivLogoMarcaGrid);
            tvNombreMarca = itemView.findViewById(R.id.tvNombreMarcaGrid);
            tvRating = itemView.findViewById(R.id.tvRatingGrid);
        }
    }
}
