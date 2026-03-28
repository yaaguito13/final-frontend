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
import com.example.moda.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private List<Categoria> categorias = new ArrayList<>();

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        
        holder.tvNombre.setText(categoria.getNombre() != null ? categoria.getNombre().toUpperCase() : "");

        Glide.with(holder.itemView.getContext())
                .load(categoria.getImagenUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvNombre;

        ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvNombre = itemView.findViewById(R.id.tvNombreCategoria);
        }
    }
}
