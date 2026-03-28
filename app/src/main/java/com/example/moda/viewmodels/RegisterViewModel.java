package com.example.moda.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moda.repositories.AuthRepository;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private AuthRepository repository;
    private MutableLiveData<Boolean> registerSuccess;
    private MutableLiveData<String> registerError;

    public RegisterViewModel() {
        repository = new AuthRepository();
        registerSuccess = new MutableLiveData<>();
        registerError = new MutableLiveData<>();
    }

    public LiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public LiveData<String> getRegisterError() {
        return registerError;
    }

    public void register(String name, String email, String password, String confirm) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            registerError.setValue("Completa todos los campos");
            return;
        }

        if (!password.equals(confirm)) {
            registerError.setValue("Las contraseñas no coinciden");
            return;
        }

        repository.register(name, email, password, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    registerSuccess.setValue(true);
                } else {
                    try {
                        registerError.setValue("Error " + response.code() + ": " + response.errorBody().string());
                    } catch (Exception e) {
                        registerError.setValue("Error en el registro. Verifica los datos.");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                registerError.setValue("Error de red: " + t.getMessage());
            }
        });
    }
}
