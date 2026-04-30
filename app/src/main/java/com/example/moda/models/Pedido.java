package com.example.moda.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pedido {
    @SerializedName("pedido_id")
    private int pedidoId;
    
    private String fecha;
    private String estado;
    private double total;
    private List<ItemPedido> articulos;

    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstado() { return estado != null ? estado : ""; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<ItemPedido> getArticulos() { return articulos; }
    public void setArticulos(List<ItemPedido> articulos) { this.articulos = articulos; }
}
