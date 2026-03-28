package com.example.moda.repositories;

import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.LoginRequest;
import com.example.moda.models.LoginResponse;
import com.example.moda.models.RegisterRequest;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class AuthRepository {

    private ApiService apiService;

    public AuthRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void login(String email, String password, Callback<LoginResponse> callback) {
        LoginRequest request = new LoginRequest(email, password);
        apiService.loginUsuario(request).enqueue(callback);
    }

    public void register(String name, String email, String password, Callback<ResponseBody> callback) {
        RegisterRequest request = new RegisterRequest(name, email, password);
        apiService.registrarUsuario(request).enqueue(callback);
    }
}
