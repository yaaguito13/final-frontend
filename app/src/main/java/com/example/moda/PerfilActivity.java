package com.example.moda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.PerfilResponse;
import com.example.moda.models.UsuarioPerfil;
import com.example.moda.utils.NavigationUtils;
import com.example.moda.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileEmail, tvProfileDate;
    private LinearLayout llCerrarSesion, llHistorialPedidos, llMisFavoritos, llDirecciones;
    
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        NavigationUtils.setupBottomNav(this, NavigationUtils.MENU_PERFIL);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        initViews();
        setupListeners();

        int usuarioId = sessionManager.getUserId();
        if (usuarioId != -1) {
            cargarPerfil(usuarioId);
        } else {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
            cerrarSesion();
        }
    }

    private void initViews() {
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileDate = findViewById(R.id.tvProfileDate);
        llCerrarSesion = findViewById(R.id.llCerrarSesion);
        llHistorialPedidos = findViewById(R.id.llHistorialPedidos);
        llMisFavoritos = findViewById(R.id.llMisFavoritos);
        llDirecciones = findViewById(R.id.llDirecciones);
    }

    private void setupListeners() {
        llCerrarSesion.setOnClickListener(v -> cerrarSesion());
        llHistorialPedidos.setOnClickListener(v -> startActivity(new Intent(PerfilActivity.this, PedidosActivity.class)));
        llMisFavoritos.setOnClickListener(v -> startActivity(new Intent(PerfilActivity.this, FavoritosActivity.class)));
        llDirecciones.setOnClickListener(v -> startActivity(new Intent(PerfilActivity.this, DireccionesActivity.class)));
    }

    private void cargarPerfil(int usuarioId) {
        apiService.getPerfil(usuarioId).enqueue(new Callback<PerfilResponse>() {
            @Override
            public void onResponse(Call<PerfilResponse> call, Response<PerfilResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UsuarioPerfil perfil = response.body().getPerfil();
                    if (perfil != null) {
                        tvProfileName.setText(perfil.getUsername());
                        tvProfileEmail.setText(perfil.getEmail());
                        tvProfileDate.setText("Registrado: " + perfil.getFechaRegistro());
                    }
                } else {
                    Toast.makeText(PerfilActivity.this, "Error al cargar perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PerfilResponse> call, Throwable t) {
                Toast.makeText(PerfilActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cerrarSesion() {
        // Borrar datos del SessionManager (SharedPreferences)
        sessionManager.clearSession();

        // Redirigir a Login y limpiar el backstack
        Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
