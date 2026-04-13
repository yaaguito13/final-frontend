package com.example.moda.repositories;

import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.CategoriaResponse;
import com.example.moda.models.MarcaResponse;
import com.example.moda.models.ProductoResponse;

import java.util.List;

import retrofit2.Callback;

public class HomeRepository {

    private ApiService apiService;

    public HomeRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void getCategorias(Callback<CategoriaResponse> callback) {
        apiService.getCategorias().enqueue(callback);
    }

    public void getMarcas(Callback<MarcaResponse> callback) {
        apiService.getMarcas().enqueue(callback);
    }

    public void getProductos(Callback<ProductoResponse> callback) {
        apiService.getProductos().enqueue(callback);
    }
}
