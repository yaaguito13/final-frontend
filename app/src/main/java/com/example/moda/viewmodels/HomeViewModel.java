package com.example.moda.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moda.models.Categoria;
import com.example.moda.models.Marca;
import com.example.moda.models.Producto;
import com.example.moda.repositories.HomeRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private HomeRepository repository;

    private MutableLiveData<List<Categoria>> categorias = new MutableLiveData<>();
    private MutableLiveData<List<Marca>> marcas = new MutableLiveData<>();
    private MutableLiveData<List<Producto>> productos = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public HomeViewModel() {
        repository = new HomeRepository();
    }

    public LiveData<List<Categoria>> getCategorias() { return categorias; }
    public LiveData<List<Marca>> getMarcas() { return marcas; }
    public LiveData<List<Producto>> getProductos() { return productos; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getLoading() { return loading; }

    public void cargarDatosHome() {
        loading.setValue(true);
        cargarCategorias();
        cargarMarcas();
        cargarProductos();
    }

    private void cargarCategorias() {
        repository.getCategorias(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    categorias.setValue(response.body());
                } else {
                    error.setValue("Error cargando categorías");
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                error.setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

    private void cargarMarcas() {
        repository.getMarcas(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                if (response.isSuccessful()) {
                    marcas.setValue(response.body());
                } else {
                    error.setValue("Error cargando marcas");
                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    private void cargarProductos() {
        repository.getProductos(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    productos.setValue(response.body());
                } else {
                    error.setValue("Error cargando productos");
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }
}
