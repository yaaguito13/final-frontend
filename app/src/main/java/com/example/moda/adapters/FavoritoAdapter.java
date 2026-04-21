package com.example.moda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moda.R;
import com.example.moda.models.Producto;

import java.util.ArrayList;
import java.util.List;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.ViewHolder> {
    
    private List<Producto> favoritos = new ArrayList<>();
    private OnFavoritoDeleteClickListener deleteClickListener;

    public interface OnFavoritoDeleteClickListener {
        void onDeleteClick(Producto producto, int position);
    }

    public void setFavoritos(List<Producto> favoritos, OnFavoritoDeleteClickListener listener) {
        this.favoritos = favoritos;
        this.deleteClickListener = listener;
        notifyDataSetChanged();
    }

    public void removeFavorito(int position) {
        if (position >= 0 && position < favoritos.size()) {
            favoritos.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = favoritos.get(position);
        
        holder.tvBrand.setText(producto.getMarcaNombre() != null ? producto.getMarcaNombre().toUpperCase() : "MARCA");
        holder.tvName.setText(producto.getNombre());
        holder.tvPrice.setText(String.format("%.2f €", producto.getPrecio()));

        String imageUrl = producto.getImagenUrl();
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
                .into(holder.ivImage);

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(producto, position); // Pass position back
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvBrand, tvName, tvPrice;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivFavoritoImage);
            tvBrand = itemView.findViewById(R.id.tvFavoritoBrand);
            tvName = itemView.findViewById(R.id.tvFavoritoName);
            tvPrice = itemView.findViewById(R.id.tvFavoritoPrice);
            btnDelete = itemView.findViewById(R.id.btnDeleteFavorito);
        }
    }
}
