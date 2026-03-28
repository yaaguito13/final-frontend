package com.example.moda.models;

public class Marca {
    private int id;
    private String nombre;
    private String logo_url;
    private String imagen_fondo_url;
    private String descripcion;
    private double rating;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getLogoUrl() { return logo_url; }
    public void setLogoUrl(String logo_url) { this.logo_url = logo_url; }

    public String getImagenFondoUrl() { return imagen_fondo_url; }
    public void setImagenFondoUrl(String imagen_fondo_url) { this.imagen_fondo_url = imagen_fondo_url; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
