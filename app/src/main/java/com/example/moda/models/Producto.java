package com.example.moda.models;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String imagen_url;
    private String marca_nombre;
    private double rating;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getImagenUrl() { return imagen_url; }
    public void setImagenUrl(String imagen_url) { this.imagen_url = imagen_url; }

    public String getMarcaNombre() { return marca_nombre; }
    public void setMarcaNombre(String marca_nombre) { this.marca_nombre = marca_nombre; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
