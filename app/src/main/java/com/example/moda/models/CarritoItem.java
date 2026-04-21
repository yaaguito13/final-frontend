package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class CarritoItem {
    private int id; // ID of the row in the cart table
    
    // Asumimos que el backend Serializa el objeto Producto entero para darnos info visual (nombre, precio, foto)
    private Producto producto;
    
    private int cantidad;
    private String talla;
    private String color;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getTalla() { return talla != null ? talla : ""; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getColor() { return color != null ? color : ""; }
    public void setColor(String color) { this.color = color; }
    
    // Método para calcular subtotal localmente independientemente de si el backend lo da o no
    public double getSubtotal() {
        if (producto != null) {
            return producto.getPrecio() * cantidad;
        }
        return 0.0;
    }
}
