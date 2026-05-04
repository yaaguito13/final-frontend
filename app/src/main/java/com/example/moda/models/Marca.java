package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class Marca {
    private int id;
    private String nombre;
    
    @SerializedName("imagen")
    private String imagen; // Campo único para el backend
    
    private String descripcion;
    private double rating;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // Ambos métodos devuelven la misma imagen del servidor
    public String getLogoUrl() { return imagen; }
    public String getImagenFondoUrl() { return imagen; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
