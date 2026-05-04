package com.example.moda.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Producto {
    private int id;
    
    @SerializedName(value = "producto_id", alternate = {"producto"})
    private Integer productoIdReal;

    private String nombre;
    private double precio;
    
    @SerializedName("imagen")
    private String imagen_url;
    
    @SerializedName("marca")
    private String marca_nombre;

    @SerializedName("marca_id")
    private Integer marcaId;

    @SerializedName("categorias")
    private List<String> categoriasNombres;
    
    private double rating;
    private String descripcion;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getProductoIdReal() { return productoIdReal != null ? productoIdReal : id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getImagenUrl() { return imagen_url; }
    public void setImagenUrl(String imagen_url) { this.imagen_url = imagen_url; }

    public String getMarcaNombre() { return marca_nombre; }
    public void setMarcaNombre(String marca_nombre) { this.marca_nombre = marca_nombre; }
    
    public Integer getMarcaId() { return marcaId; }
    public void setMarcaId(Integer marcaId) { this.marcaId = marcaId; }

    public List<String> getCategoriasNombres() { return categoriasNombres; }
    public void setCategoriasNombres(List<String> categorias) { this.categoriasNombres = categorias; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
