package com.example.moda.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moda.models.Categoria;
import com.example.moda.models.CategoriaResponse;
import com.example.moda.models.Marca;
import com.example.moda.models.MarcaResponse;
import com.example.moda.models.Producto;
import com.example.moda.models.ProductoResponse;
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
        repository.getCategorias(new Callback<CategoriaResponse>() {
            @Override
            public void onResponse(Call<CategoriaResponse> call, Response<CategoriaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categorias.setValue(response.body().getCategorias());
                } else {
                    error.setValue("Error cargando categorías");
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<CategoriaResponse> call, Throwable t) {
                error.setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

    private void cargarMarcas() {
        repository.getMarcas(new Callback<MarcaResponse>() {
            @Override
            public void onResponse(Call<MarcaResponse> call, Response<MarcaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    marcas.setValue(response.body().getMarcas());
                } else {
                    error.setValue("Error cargando marcas");
                }
            }

            @Override
            public void onFailure(Call<MarcaResponse> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    private void cargarProductos() {
        repository.getProductos(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productos.setValue(response.body().getProductos());
                } else {
                    error.setValue("Error cargando productos");
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }
}
