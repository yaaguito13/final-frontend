package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class ItemPedido {
    private String nombre;
    private int cantidad;
    private String talla;
    private String color;
    
    @SerializedName("precio_unitario")
    private double precioUnitario;
    
    private String imagen;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getTalla() { return talla != null ? talla : ""; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getColor() { return color != null ? color : ""; }
    public void setColor(String color) { this.color = color; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}
