package com.example.moda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moda.adapters.ProductoAdapter;
import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.Producto;
import com.example.moda.models.ProductoResponse;
import com.example.moda.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarcaDetailActivity extends AppCompatActivity {

    private ImageView ivBanner, ivLogo;
    private TextView tvBrandName, tvDescription, tvRating;
    private ImageButton btnBack, btnCart, btnFavorite;
    private RecyclerView rvBrandProducts;
    
    private ApiService apiService;
    private ProductoAdapter productoAdapter;
    private int marcaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca_detail);

        // Inicializar vistas
        ivBanner = findViewById(R.id.ivBanner);
        ivLogo = findViewById(R.id.ivLogo);
        tvBrandName = findViewById(R.id.tvBrandName);
        tvDescription = findViewById(R.id.tvDescription);
        tvRating = findViewById(R.id.tvRating);
        btnBack = findViewById(R.id.btnBack);
        btnCart = findViewById(R.id.btnCart);
        btnFavorite = findViewById(R.id.btnFavorite);
        rvBrandProducts = findViewById(R.id.rvBrandProducts);

        apiService = ApiClient.getClient().create(ApiService.class);

        // Recuperar datos del Intent
        marcaId = getIntent().getIntExtra("MARCA_ID", -1);
        String nombre = getIntent().getStringExtra("MARCA_NOMBRE");
        String descripcion = getIntent().getStringExtra("MARCA_DESC");
        String logoUrl = getIntent().getStringExtra("MARCA_LOGO");
        String bannerUrl = getIntent().getStringExtra("MARCA_BANNER");
        double rating = getIntent().getDoubleExtra("MARCA_RATING", 4.5);

        // Llenar UI
        tvBrandName.setText(nombre);
        tvDescription.setText(descripcion);
        tvRating.setText(String.valueOf(rating));

        cargarImagen(logoUrl, ivLogo);
        cargarImagen(bannerUrl, ivBanner);

        // Configurar RecyclerView
        productoAdapter = new ProductoAdapter();
        rvBrandProducts.setAdapter(productoAdapter);

        // Navegación
        btnBack.setOnClickListener(v -> finish());
        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CarritoActivity.class));
        });

        // Favoritos de Marca (Deshabilitado por ahora)
        btnFavorite.setOnClickListener(v -> {
            Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show();
        });

        NavigationUtils.setupBottomNav(this, -1); // No highlight in brand detail usually

        // Cargar productos
        cargarProductosMarca();
    }

    private void cargarImagen(String url, ImageView imageView) {
        if (url != null) {
            String fixedUrl = url.replace("127.0.0.1", "10.0.2.2").replace("localhost", "10.0.2.2");
            Glide.with(this).load(fixedUrl).placeholder(android.R.drawable.ic_menu_gallery).into(imageView);
        }
    }

    private void cargarProductosMarca() {
        // Obtenemos el nombre de la marca que pasamos por Intent para filtrar localmente
        String nombreMarcaBusqueda = getIntent().getStringExtra("MARCA_NOMBRE");

        apiService.getProductos(marcaId, null).enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Producto> todosLosProductos = response.body().getProductos();
                    List<Producto> productosFiltrados = new ArrayList<>();

                    if (todosLosProductos != null) {
                        String filterToken = (nombreMarcaBusqueda != null) ? nombreMarcaBusqueda.trim().toLowerCase() : "";
                        for (Producto p : todosLosProductos) {
                            String pMarcaName = (p.getMarcaNombre() != null) ? p.getMarcaNombre().trim().toLowerCase() : "";
                            Integer pMarcaId = p.getMarcaId();
                            
                            boolean matchId = (marcaId != -1 && pMarcaId != null && pMarcaId.intValue() == marcaId);
                            boolean matchName = (!filterToken.isEmpty() && pMarcaName.equals(filterToken));
                            
                            android.util.Log.d("MARCA_FILTER_DEBUG", "Original: " + pMarcaName + " | Buscando: " + filterToken + " | Match: " + (matchId || matchName));

                            if (matchId || matchName) {
                                productosFiltrados.add(p);
                            }
                        }
                    }
                    android.util.Log.e("MARCA_FILTER_RESULT", "Final Count: " + productosFiltrados.size() + " out of " + (todosLosProductos != null ? todosLosProductos.size() : 0));
                    productoAdapter.setProductos(productosFiltrados);
                } else {
                    Toast.makeText(MarcaDetailActivity.this, "Error al cargar catálogo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Toast.makeText(MarcaDetailActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
