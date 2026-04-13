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
import com.example.moda.models.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private List<Producto> productos = new ArrayList<>();

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        
        holder.tvMarca.setText(producto.getMarcaNombre() != null ? producto.getMarcaNombre().toUpperCase() : "");
        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText(String.format("%.2f €", producto.getPrecio()));
        holder.tvRating.setText("★ " + producto.getRating());

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
                .into(holder.ivProducto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProducto;
        TextView tvMarca, tvNombre, tvPrecio, tvRating;

        ViewHolder(View itemView) {
            super(itemView);
            ivProducto = itemView.findViewById(R.id.ivProducto);
            tvMarca = itemView.findViewById(R.id.tvMarca);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
