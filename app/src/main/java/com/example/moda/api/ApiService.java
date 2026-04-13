package com.example.moda.api;

import com.example.moda.models.LoginRequest;
import com.example.moda.models.LoginResponse;
import com.example.moda.models.RegisterRequest;
import com.example.moda.models.CategoriaResponse;
import com.example.moda.models.MarcaResponse;
import com.example.moda.models.ProductoResponse;

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
    Call<CategoriaResponse> getCategorias();

    @GET("marcas/")
    Call<MarcaResponse> getMarcas();

    @GET("productos/")
    Call<ProductoResponse> getProductos();
}
