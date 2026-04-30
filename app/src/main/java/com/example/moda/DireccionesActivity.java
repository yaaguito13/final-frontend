package com.example.moda;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moda.adapters.DireccionAdapter;
import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.DireccionRequest;
import com.example.moda.models.DireccionResponse;
import com.example.moda.utils.SessionManager;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DireccionesActivity extends AppCompatActivity implements DireccionAdapter.OnDireccionAction {

    private ImageButton ivBack;
    private RecyclerView rvDirecciones;
    private DireccionAdapter adapter;
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcciones);

        ivBack = findViewById(R.id.ivBack);
        rvDirecciones = findViewById(R.id.rvDirecciones);

        ivBack.setOnClickListener(v -> finish());

        rvDirecciones.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DireccionAdapter(this, new ArrayList<>(), this);
        rvDirecciones.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);
        sessionManager = new SessionManager(this);

        cargarDirecciones();
    }

    private void cargarDirecciones() {
        int usuarioId = sessionManager.getUserId();
        if (usuarioId == -1) {
            Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getDirecciones(usuarioId).enqueue(new Callback<DireccionResponse>() {
            @Override
            public void onResponse(Call<DireccionResponse> call, Response<DireccionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getDirecciones() != null) {
                        adapter.setDirecciones(response.body().getDirecciones());
                    }
                } else {
                    Toast.makeText(DireccionesActivity.this, "Error al cargar direcciones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DireccionResponse> call, Throwable t) {
                Toast.makeText(DireccionesActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onGuardar(String titulo, String direccionCompleta) {
        int usuarioId = sessionManager.getUserId();
        if (usuarioId == -1) return;

        // Mandamos todo el texto del diseño al campo 'calle' y dejamos los otros en blanco.
        DireccionRequest request = new DireccionRequest(usuarioId, titulo, direccionCompleta, "", "");

        apiService.addDireccion(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DireccionesActivity.this, "Dirección guardada con éxito", Toast.LENGTH_SHORT).show();
                    cargarDirecciones(); // Recargar la lista desde el servidor
                } else {
                    Toast.makeText(DireccionesActivity.this, "Error al guardar la dirección", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DireccionesActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
