package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class CarritoItem {
    @SerializedName(value = "id", alternate = {"pk", "item_id", "carrito_id"})
    private int id; // ID of the row in the cart table
    
    @SerializedName("producto_id")
    private int productoId;
    
    private String nombre;
    
    @SerializedName("precio_unitario")
    private double precioUnitario;
    
    private String imagen;
    
    private int cantidad;
    private String talla;
    private String color;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getTalla() { return talla != null ? talla : ""; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getColor() { return color != null ? color : ""; }
    public void setColor(String color) { this.color = color; }
    
    public double getSubtotal() {
        return precioUnitario * cantidad;
    }
}
