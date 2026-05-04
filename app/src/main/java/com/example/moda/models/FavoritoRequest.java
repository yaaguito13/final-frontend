package com.example.moda.models;

import com.google.gson.annotations.SerializedName;

public class FavoritoRequest {
    @SerializedName("usuario_id")
    private int usuarioId;

    @SerializedName("producto_id")
    private Integer productoId;

    @SerializedName("marca_id")
    private Integer marcaId;

    public FavoritoRequest(int usuarioId, Integer productoId) {
        this.usuarioId = usuarioId;
        this.productoId = productoId;
    }

    public FavoritoRequest(int usuarioId, Integer productoId, Integer marcaId) {
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.marcaId = marcaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }
}
