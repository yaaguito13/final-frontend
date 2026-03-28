package com.example.moda.api;

import com.example.moda.models.LoginRequest;
import com.example.moda.models.LoginResponse;
import com.example.moda.models.RegisterRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    
    @POST("login/")
    Call<LoginResponse> loginUsuario(@Body LoginRequest request);

    @POST("registro/")
    Call<ResponseBody> registrarUsuario(@Body RegisterRequest request);
}
