package com.example.moda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moda.adapters.FavoritoAdapter;
import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.Producto;
import com.example.moda.utils.SessionManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritosActivity extends AppCompatActivity {

    private RecyclerView rvFavoritos;
    private LinearLayout llEmptyState;
    private ImageButton btnBack;

    private FavoritoAdapter adapter;
    private ApiService apiService;
    private SessionManager sessionManager;
    private int usuarioId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        apiService = ApiClient.getClient().create(ApiService.class);
        sessionManager = new SessionManager(this);
        usuarioId = sessionManager.getUserId();

        initViews();
        setupRecyclerView();

        com.example.moda.utils.NavigationUtils.setupBottomNav(this, com.example.moda.utils.NavigationUtils.MENU_FAVORITOS);

        btnBack.setOnClickListener(v -> finish());

        findViewById(R.id.ivCart).setOnClickListener(v -> {
            Intent intent = new Intent(this, CarritoActivity.class);
            startActivity(intent);
        });

        if (usuarioId != -1) {
            cargarFavoritos();
        } else {
            Toast.makeText(this, "Debes iniciar sesión para ver tus favoritos.", Toast.LENGTH_SHORT).show();
            showEmptyState();
        }
    }

    private void initViews() {
        rvFavoritos = findViewById(R.id.rvFavoritos);
        llEmptyState = findViewById(R.id.llEmptyState);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupRecyclerView() {
        adapter = new FavoritoAdapter();
        rvFavoritos.setLayoutManager(new LinearLayoutManager(this));
        rvFavoritos.setAdapter(adapter);
    }

    private void cargarFavoritos() {
        apiService.getFavoritos(usuarioId).enqueue(new Callback<com.example.moda.models.FavoritoResponse>() {
            @Override
            public void onResponse(Call<com.example.moda.models.FavoritoResponse> call, Response<com.example.moda.models.FavoritoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Producto> favoritos = response.body().getFavoritos();
                    if (favoritos == null || favoritos.isEmpty()) {
                        showEmptyState();
                    } else {
                        showList();
                        adapter.setFavoritos(favoritos, (producto, position) -> eliminarFavorito(producto, position));
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown API error";
                        android.widget.Toast.makeText(FavoritosActivity.this, "Error API: " + errorBody, android.widget.Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showEmptyState();
                }
            }

            @Override
            public void onFailure(Call<com.example.moda.models.FavoritoResponse> call, Throwable t) {
                android.widget.Toast.makeText(FavoritosActivity.this, "Error: " + t.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                showEmptyState();
            }
        });
    }

    private void eliminarFavorito(Producto producto, int position) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Eliminar Favorito")
                .setMessage("¿Estás seguro de que quieres eliminar este producto de tus favoritos?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    apiService.deleteFavorito(producto.getProductoIdReal(), usuarioId).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                adapter.removeFavorito(position);
                                android.widget.Toast.makeText(FavoritosActivity.this, "Eliminado de favoritos", android.widget.Toast.LENGTH_SHORT).show();
                                
                                if (adapter.getItemCount() == 0) {
                                    showEmptyState();
                                }
                            } else {
                                try {
                                    String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error " + response.code();
                                    android.widget.Toast.makeText(FavoritosActivity.this, "Error API: " + errorBody, android.widget.Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            android.widget.Toast.makeText(FavoritosActivity.this, "Error de red: " + t.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showEmptyState() {
        rvFavoritos.setVisibility(View.GONE);
        llEmptyState.setVisibility(View.VISIBLE);
    }

    private void showList() {
        rvFavoritos.setVisibility(View.VISIBLE);
        llEmptyState.setVisibility(View.GONE);
    }
}
