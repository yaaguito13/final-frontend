package com.example.moda.models;

import java.util.List;

public class PedidoResponse {
    private List<Pedido> pedidos;

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}
