package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class PerfilResponse {
    @SerializedName("perfil")
    private UsuarioPerfil perfil;

    public UsuarioPerfil getPerfil() { return perfil; }
    public void setPerfil(UsuarioPerfil perfil) { this.perfil = perfil; }
}
