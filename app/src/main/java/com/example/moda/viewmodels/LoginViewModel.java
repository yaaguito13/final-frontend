package com.example.moda.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moda.models.LoginResponse;
import com.example.moda.repositories.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private AuthRepository repository;
    private MutableLiveData<Integer> loginSuccess;
    private MutableLiveData<String> loginError;

    public LoginViewModel() {
        repository = new AuthRepository();
        loginSuccess = new MutableLiveData<>();
        loginError = new MutableLiveData<>();
    }

    public LiveData<Integer> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            loginError.setValue("Todos los campos son obligatorios");
            return;
        }

        repository.login(email, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginSuccess.setValue(response.body().getId());
                } else {
                    loginError.setValue("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginError.setValue("Error de red: " + t.getMessage());
            }
        });
    }
}
