package com.example.moda;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moda.adapters.MarcaGridAdapter;
import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.Marca;
import com.example.moda.models.MarcaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodasLasMarcasActivity extends AppCompatActivity {

    private ImageButton ivBack;
    private EditText etBuscarMarca;
    private RecyclerView rvTodasMarcas;
    private MarcaGridAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_marcas);

        ivBack = findViewById(R.id.ivBack);
        etBuscarMarca = findViewById(R.id.etBuscarMarca);
        rvTodasMarcas = findViewById(R.id.rvTodasMarcas);

        ivBack.setOnClickListener(v -> finish());

        // Grid 2 columns
        rvTodasMarcas.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MarcaGridAdapter();
        rvTodasMarcas.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        cargarMarcas();

        // Pre-fill search if launched from home search bar
        String query = getIntent().getStringExtra("QUERY");
        if (query != null && !query.isEmpty()) {
            etBuscarMarca.setText(query);
        }

        // Search filter
        etBuscarMarca.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtrar(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void cargarMarcas() {
        apiService.getMarcas().enqueue(new Callback<MarcaResponse>() {
            @Override
            public void onResponse(Call<MarcaResponse> call, Response<MarcaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setMarcas(response.body().getMarcas(), marca -> {
                        Intent intent = new Intent(TodasLasMarcasActivity.this, MarcaDetailActivity.class);
                        intent.putExtra("MARCA_ID", marca.getId());
                        intent.putExtra("MARCA_NOMBRE", marca.getNombre());
                        intent.putExtra("MARCA_DESC", marca.getDescripcion());
                        intent.putExtra("MARCA_LOGO", marca.getLogoUrl());
                        intent.putExtra("MARCA_BANNER", marca.getImagenFondoUrl());
                        intent.putExtra("MARCA_RATING", marca.getRating());
                        startActivity(intent);
                    });

                    // Apply query AFTER data is loaded
                    String currentQuery = etBuscarMarca.getText().toString().trim();
                    if (!currentQuery.isEmpty()) {
                        adapter.filtrar(currentQuery);
                    }
                } else {
                    Toast.makeText(TodasLasMarcasActivity.this, "Error al cargar marcas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MarcaResponse> call, Throwable t) {
                Toast.makeText(TodasLasMarcasActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
