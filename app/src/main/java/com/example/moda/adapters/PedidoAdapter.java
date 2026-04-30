package com.example.moda.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moda.R;
import com.example.moda.models.ItemPedido;
import com.example.moda.models.Pedido;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> pedidoList;
    private Context context;

    public PedidoAdapter(Context context, List<Pedido> pedidoList) {
        this.context = context;
        this.pedidoList = pedidoList;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidoList = pedidos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidoList.get(position);

        holder.tvPedidoId.setText("Pedido #" + pedido.getPedidoId());
        holder.tvPedidoFecha.setText(pedido.getFecha().toUpperCase());
        holder.tvPedidoTotal.setText(String.format("%.2f €", pedido.getTotal()));
        
        List<ItemPedido> articulos = pedido.getArticulos();
        int numArticulos = articulos != null ? articulos.size() : 0;
        holder.tvTotalArticulos.setText(numArticulos + (numArticulos == 1 ? " ARTÍCULO" : " ARTÍCULOS"));

        // Estado format
        String estado = pedido.getEstado().toUpperCase();
        holder.tvPedidoEstado.setText(estado);
        if (estado.contains("ENTREGADO")) {
            holder.tvPedidoEstado.setTextColor(Color.parseColor("#009966")); // Greenish
        } else {
            holder.tvPedidoEstado.setTextColor(Color.parseColor("#4466FF")); // Blueish
        }

        // Initially hide details
        holder.llArticulosContainer.setVisibility(View.GONE);
        holder.vDividerBottom.setVisibility(View.GONE);
        holder.tvToggleDetalles.setText("VER DETALLES  >");
        holder.llArticulosContainer.removeAllViews(); // Clear previous views

        // Setup expansible logic
        holder.tvToggleDetalles.setOnClickListener(v -> {
            if (holder.llArticulosContainer.getVisibility() == View.GONE) {
                // Expand
                holder.llArticulosContainer.setVisibility(View.VISIBLE);
                holder.vDividerBottom.setVisibility(View.VISIBLE);
                holder.tvToggleDetalles.setText("OCULTAR DETALLES  v");
                
                // Populate if empty
                if (holder.llArticulosContainer.getChildCount() == 0 && articulos != null) {
                    for (ItemPedido item : articulos) {
                        View itemView = LayoutInflater.from(context).inflate(R.layout.item_pedido_articulo, holder.llArticulosContainer, false);
                        
                        TextView tvNombre = itemView.findViewById(R.id.tvArticuloNombre);
                        TextView tvDetalles = itemView.findViewById(R.id.tvArticuloDetalles);
                        TextView tvSubtotal = itemView.findViewById(R.id.tvArticuloSubtotal);
                        ImageView ivImagen = itemView.findViewById(R.id.ivArticuloImagen);
                        
                        tvNombre.setText(item.getNombre());
                        
                        String detallesStr = item.getCantidad() + " X " + String.format("%.2f €", item.getPrecioUnitario());
                        if (!item.getTalla().isEmpty()) detallesStr += " • " + item.getTalla().toUpperCase();
                        if (!item.getColor().isEmpty()) detallesStr += " • " + item.getColor().toUpperCase();
                        tvDetalles.setText(detallesStr);
                        
                        double subtotal = item.getPrecioUnitario() * item.getCantidad();
                        tvSubtotal.setText(String.format("%.2f €", subtotal));
                        
                        String imgUrl = item.getImagen();
                        if (imgUrl != null) {
                            if (imgUrl.contains("127.0.0.1") || imgUrl.contains("localhost")) {
                                imgUrl = imgUrl.replace("127.0.0.1", "10.0.2.2").replace("localhost", "10.0.2.2");
                            }
                            Glide.with(context).load(imgUrl).placeholder(android.R.drawable.ic_menu_gallery).into(ivImagen);
                        }
                        
                        holder.llArticulosContainer.addView(itemView);
                    }
                }
            } else {
                // Collapse
                holder.llArticulosContainer.setVisibility(View.GONE);
                holder.vDividerBottom.setVisibility(View.GONE);
                holder.tvToggleDetalles.setText("VER DETALLES  >");
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidoList != null ? pedidoList.size() : 0;
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvPedidoId, tvPedidoFecha, tvPedidoEstado, tvToggleDetalles, tvTotalArticulos, tvPedidoTotal;
        LinearLayout llArticulosContainer;
        View vDividerBottom;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPedidoId = itemView.findViewById(R.id.tvPedidoId);
            tvPedidoFecha = itemView.findViewById(R.id.tvPedidoFecha);
            tvPedidoEstado = itemView.findViewById(R.id.tvPedidoEstado);
            tvToggleDetalles = itemView.findViewById(R.id.tvToggleDetalles);
            tvTotalArticulos = itemView.findViewById(R.id.tvTotalArticulos);
            tvPedidoTotal = itemView.findViewById(R.id.tvPedidoTotal);
            llArticulosContainer = itemView.findViewById(R.id.llArticulosContainer);
            vDividerBottom = itemView.findViewById(R.id.vDividerBottom);
        }
    }
}
