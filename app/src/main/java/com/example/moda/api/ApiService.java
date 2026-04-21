package com.example.moda.api;

import com.example.moda.models.LoginRequest;
import com.example.moda.models.LoginResponse;
import com.example.moda.models.RegisterRequest;
import com.example.moda.models.CategoriaResponse;
import com.example.moda.models.MarcaResponse;
import com.example.moda.models.ProductoResponse;
import com.example.moda.models.Producto;
import com.example.moda.models.FavoritoRequest;
import com.example.moda.models.CarritoRequest;

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

    @GET("productos/{producto_id}/")
    Call<Producto> getProducto(@retrofit2.http.Path("producto_id") int productoId);

    @GET("favoritos/")
    Call<com.example.moda.models.FavoritoResponse> getFavoritos(@retrofit2.http.Query("usuario_id") int usuarioId);

    @POST("favoritos/")
    Call<ResponseBody> addFavorito(@Body FavoritoRequest request);

    @retrofit2.http.DELETE("favoritos/{producto_id}/")
    Call<ResponseBody> deleteFavorito(@retrofit2.http.Path("producto_id") int productoId, @retrofit2.http.Query("usuario_id") int usuarioId);

    @POST("carrito/")
    Call<ResponseBody> addCarrito(@Body CarritoRequest request);

    @GET("carrito/")
    Call<com.example.moda.models.CarritoResponse> getCarrito(@retrofit2.http.Query("usuario_id") int usuarioId);

    @retrofit2.http.PUT("carrito/{item_id}/")
    Call<ResponseBody> updateCarrito(@retrofit2.http.Path("item_id") int itemId, @Body com.example.moda.models.UpdateQuantityRequest request);

    @retrofit2.http.DELETE("carrito/{item_id}/")
    Call<ResponseBody> deleteCarrito(@retrofit2.http.Path("item_id") int itemId);
}
