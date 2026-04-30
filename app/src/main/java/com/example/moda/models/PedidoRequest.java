package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class PedidoRequest {
    @SerializedName("usuario_id")
    private int usuarioId;

    public PedidoRequest(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getUsuarioId() { return usuarioId; }
}
