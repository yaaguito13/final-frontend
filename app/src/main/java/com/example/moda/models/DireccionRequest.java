package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class DireccionRequest {
    @SerializedName("usuario_id")
    private int usuarioId;
    
    private String titulo;
    private String calle;
    private String ciudad;
    
    @SerializedName("codigo_postal")
    private String codigoPostal;

    public DireccionRequest(int usuarioId, String titulo, String calle, String ciudad, String codigoPostal) {
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public int getUsuarioId() { return usuarioId; }
    public String getTitulo() { return titulo; }
    public String getCalle() { return calle; }
    public String getCiudad() { return ciudad; }
    public String getCodigoPostal() { return codigoPostal; }
}
