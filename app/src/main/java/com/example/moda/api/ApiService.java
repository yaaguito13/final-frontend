package com.example.moda.api;

import com.example.moda.models.LoginRequest;
import com.example.moda.models.LoginResponse;
import com.example.moda.models.RegisterRequest;
import com.example.moda.models.Categoria;
import com.example.moda.models.Marca;
import com.example.moda.models.Producto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    
    @POST("login/")
    Call<LoginResponse> loginUsuario(@Body LoginRequest request);

    @POST("registro/")
    Call<ResponseBody> registrarUsuario(@Body RegisterRequest request);

    @GET("categorias/")
    Call<List<Categoria>> getCategorias();

    @GET("marcas/")
    Call<List<Marca>> getMarcas();

    @GET("productos/")
    Call<List<Producto>> getProductos();
}
