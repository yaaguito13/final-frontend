package com.example.moda.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CarritoResponse {
    
    // Fallbacks para atrapar el nombre de la variable raíz en el ViewSet del proxy
    @SerializedName(value="carrito", alternate={"items", "results", "data", "productos"})
    private List<CarritoItem> carrito;

    public List<CarritoItem> getCarrito() {
        return carrito;
    }
}
