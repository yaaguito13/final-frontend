package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class Categoria {
    private int id;
    private String nombre;
    
    @SerializedName("imagen")
    private String imagen_url;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImagenUrl() { return imagen_url; }
    public void setImagenUrl(String imagen_url) { this.imagen_url = imagen_url; }
}
