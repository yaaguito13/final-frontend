package com.example.moda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moda.models.CarritoItem;

import java.util.ArrayList;
import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<CarritoItem> carritoList = new ArrayList<>();
    private OnCartActionListener listener;
    private Context context;

    public interface OnCartActionListener {
        void onIncreaseQuantity(CarritoItem item, int position);
        void onDecreaseQuantity(CarritoItem item, int position);
        void onDeleteItem(CarritoItem item, int position);
    }

    public void setCarrito(List<CarritoItem> carritoList, OnCartActionListener listener) {
        this.carritoList = carritoList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public void updateItem(int position, CarritoItem updatedItem) {
        if (position >= 0 && position < carritoList.size()) {
            carritoList.set(position, updatedItem);
            notifyItemChanged(position);
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < carritoList.size()) {
            carritoList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, carritoList.size());
        }
    }

    public List<CarritoItem> getItems() {
        return carritoList;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        CarritoItem item = carritoList.get(position);

        if (item.getProducto() != null) {
            holder.tvName.setText(item.getProducto().getNombre());
            holder.tvBrand.setText(item.getProducto().getMarcaNombre());

            String imgUrl = item.getProducto().getImagenUrl();
            if (imgUrl != null) {
                if (imgUrl.contains("127.0.0.1") || imgUrl.contains("localhost")) {
                    imgUrl = imgUrl.replace("127.0.0.1", "10.0.2.2").replace("localhost", "10.0.2.2");
                }
                Glide.with(context).load(imgUrl).placeholder(android.R.drawable.ic_menu_gallery).into(holder.ivProductImage);
            }
        }

        holder.tvVariants.setText("Talla: " + item.getTalla() + " | Color: " + item.getColor());
        holder.tvQuantity.setText(String.valueOf(item.getCantidad()));
        holder.tvSubtotal.setText(String.format("%.2f €", item.getSubtotal()));

        holder.btnPlus.setOnClickListener(v -> {
            if (listener != null) listener.onIncreaseQuantity(item, position);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (listener != null) listener.onDecreaseQuantity(item, position);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteItem(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return carritoList.size();
    }

    static class CarritoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvBrand, tvName, tvVariants, tvSubtotal, tvQuantity;
        ImageButton btnMinus, btnPlus, btnDelete;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvName = itemView.findViewById(R.id.tvName);
            tvVariants = itemView.findViewById(R.id.tvVariants);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
