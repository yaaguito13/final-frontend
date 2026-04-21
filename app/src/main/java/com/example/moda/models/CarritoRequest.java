package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class CarritoRequest {
    @SerializedName("usuario_id")
    private int usuarioId;

    @SerializedName("producto_id")
    private int productoId;

    private int cantidad;
    private String talla;
    private String color;

    public CarritoRequest(int usuarioId, int productoId, int cantidad, String talla, String color) {
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.talla = talla;
        this.color = color;
    }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
