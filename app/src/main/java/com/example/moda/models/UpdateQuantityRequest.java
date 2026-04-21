package com.example.moda.models;

public class UpdateQuantityRequest {
    private int cantidad;

    public UpdateQuantityRequest(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
