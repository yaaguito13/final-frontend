package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class FavoritoRequest {
    @SerializedName("usuario_id")
    private int usuarioId;

    @SerializedName("producto_id")
    private int productoId;

    public FavoritoRequest(int usuarioId, int productoId) {
        this.usuarioId = usuarioId;
        this.productoId = productoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}
