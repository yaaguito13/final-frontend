package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class UsuarioPerfil {
    private int id;
    private String username;
    private String email;
    
    @SerializedName("fecha_registro")
    private String fechaRegistro;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
