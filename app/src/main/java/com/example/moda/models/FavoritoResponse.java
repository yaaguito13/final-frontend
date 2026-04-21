package com.example.moda.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FavoritoResponse {
    
    // Cubrimos las posibles claves que el backend Django pueda estar devolviendo
    @SerializedName(value="favoritos", alternate={"productos", "results", "data"})
    private List<Producto> favoritos;

    public List<Producto> getFavoritos() {
        return favoritos;
    }
}
