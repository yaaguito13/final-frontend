package com.example.moda;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moda.adapters.PedidoAdapter;
import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.PedidoResponse;
import com.example.moda.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidosActivity extends AppCompatActivity {

    private ImageView ivBack;
    private RecyclerView rvPedidos;
    private PedidoAdapter adapter;
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        ivBack = findViewById(R.id.ivBack);
        rvPedidos = findViewById(R.id.rvPedidos);

        ivBack.setOnClickListener(v -> finish());

        rvPedidos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PedidoAdapter(this, new ArrayList<>());
        rvPedidos.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);
        sessionManager = new SessionManager(this);

        cargarPedidos();
    }

    private void cargarPedidos() {
        int usuarioId = sessionManager.getUserId();
        if (usuarioId == -1) {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getHistorialPedidos(usuarioId).enqueue(new Callback<PedidoResponse>() {
            @Override
            public void onResponse(Call<PedidoResponse> call, Response<PedidoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getPedidos() != null && !response.body().getPedidos().isEmpty()) {
                        adapter.setPedidos(response.body().getPedidos());
                    } else {
                        Toast.makeText(PedidosActivity.this, "No tienes pedidos aún", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PedidosActivity.this, "Error al cargar pedidos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PedidoResponse> call, Throwable t) {
                Toast.makeText(PedidosActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
