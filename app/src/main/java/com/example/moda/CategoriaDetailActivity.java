package com.example.moda;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moda.adapters.ProductoAdapter;
import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.ProductoResponse;
import com.example.moda.utils.NavigationUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaDetailActivity extends AppCompatActivity {

    private ImageView ivHeaderImage;
    private TextView tvCategoriaName;
    private ImageButton btnBack;
    private RecyclerView rvCategoriaProductos;
    
    private ApiService apiService;
    private ProductoAdapter productoAdapter;
    private int categoriaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_detail);

        ivHeaderImage = findViewById(R.id.ivHeaderImage);
        tvCategoriaName = findViewById(R.id.tvCategoriaName);
        btnBack = findViewById(R.id.btnBack);
        rvCategoriaProductos = findViewById(R.id.rvCategoriaProductos);

        apiService = ApiClient.getClient().create(ApiService.class);

        // Recuperar datos del Intent
        categoriaId = getIntent().getIntExtra("CATEGORIA_ID", -1);
        String nombre = getIntent().getStringExtra("CATEGORIA_NOMBRE");
        String imagenUrl = getIntent().getStringExtra("CATEGORIA_IMAGEN");

        tvCategoriaName.setText(nombre != null ? nombre.toUpperCase() : "");

        if (imagenUrl != null) {
            String fixedUrl = imagenUrl.replace("127.0.0.1", "10.0.2.2").replace("localhost", "10.0.2.2");
            Glide.with(this).load(fixedUrl).placeholder(android.R.drawable.ic_menu_gallery).into(ivHeaderImage);
        }

        productoAdapter = new ProductoAdapter();
        rvCategoriaProductos.setAdapter(productoAdapter);

        btnBack.setOnClickListener(v -> finish());

        NavigationUtils.setupBottomNav(this, -1);

        cargarProductosCategoria();
    }

    private void cargarProductosCategoria() {
        // Obtenemos el nombre de la categoría para filtrar localmente por si acaso
        String nombreCategoriaBusqueda = getIntent().getStringExtra("CATEGORIA_NOMBRE");

        apiService.getProductos(null, categoriaId).enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    java.util.List<com.example.moda.models.Producto> todosLosProductos = response.body().getProductos();
                    java.util.List<com.example.moda.models.Producto> productosFiltrados = new java.util.ArrayList<>();

                    if (todosLosProductos != null) {
                        String filterToken = (nombreCategoriaBusqueda != null) ? nombreCategoriaBusqueda.trim().toLowerCase() : "";
                        // Singularización para el fallback de nombre
                        String singularToken = filterToken.endsWith("es") ? filterToken.substring(0, filterToken.length() - 2) : 
                                               (filterToken.endsWith("s") ? filterToken.substring(0, filterToken.length() - 1) : filterToken);

                        for (com.example.moda.models.Producto p : todosLosProductos) {
                            java.util.List<String> pCategorias = p.getCategoriasNombres();
                            String pName = (p.getNombre() != null) ? p.getNombre().toLowerCase() : "";
                            
                            boolean matchByList = false;
                            if (pCategorias != null) {
                                for (String cat : pCategorias) {
                                    if (cat.equalsIgnoreCase(nombreCategoriaBusqueda)) {
                                        matchByList = true;
                                        break;
                                    }
                                }
                            }

                            // Fallback por nombre de producto si no hay categorías asignadas o falla la lista
                            boolean matchInProductName = (!filterToken.isEmpty() && !singularToken.isEmpty() && (pName.contains(filterToken) || pName.contains(singularToken)));

                            if (matchByList || matchInProductName) {
                                productosFiltrados.add(p);
                            }
                        }
                    }

                    // Si después de todo sigue vacío, no añadimos nada y el Toast avisará
                    productoAdapter.setProductos(productosFiltrados);
                    
                    if (productosFiltrados.isEmpty()) {
                        Toast.makeText(CategoriaDetailActivity.this, "No hay productos en esta categoría", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CategoriaDetailActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Toast.makeText(CategoriaDetailActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
