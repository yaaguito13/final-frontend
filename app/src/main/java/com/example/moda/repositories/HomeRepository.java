package com.example.moda.repositories;

import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.Categoria;
import com.example.moda.models.Marca;
import com.example.moda.models.Producto;

import java.util.List;

import retrofit2.Callback;

public class HomeRepository {

    private ApiService apiService;

    public HomeRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void getCategorias(Callback<List<Categoria>> callback) {
        apiService.getCategorias().enqueue(callback);
    }

    public void getMarcas(Callback<List<Marca>> callback) {
        apiService.getMarcas().enqueue(callback);
    }

    public void getProductos(Callback<List<Producto>> callback) {
        apiService.getProductos().enqueue(callback);
    }
}
