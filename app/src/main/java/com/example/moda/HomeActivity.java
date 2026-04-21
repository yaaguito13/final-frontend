package com.example.moda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moda.adapters.CategoriaAdapter;
import com.example.moda.adapters.MarcaAdapter;
import com.example.moda.adapters.ProductoAdapter;
import com.example.moda.viewmodels.HomeViewModel;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    private CategoriaAdapter categoriaAdapter;
    private MarcaAdapter marcaAdapter;
    private ProductoAdapter productoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerViews();
        setupObservers();

        com.example.moda.utils.NavigationUtils.setupBottomNav(this, com.example.moda.utils.NavigationUtils.MENU_INICIO);

        findViewById(R.id.ivCart).setOnClickListener(v -> {
            Intent intent = new Intent(this, CarritoActivity.class);
            startActivity(intent);
        });

        // Lanzar las 3 llamadas paralelas
        viewModel.cargarDatosHome();
    }

    private void setupRecyclerViews() {
        // Categorias (Horiz)
        RecyclerView rvCategorias = findViewById(R.id.rvCategorias);
        rvCategorias.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriaAdapter = new CategoriaAdapter();
        rvCategorias.setAdapter(categoriaAdapter);

        // Marcas (Horiz)
        RecyclerView rvMarcas = findViewById(R.id.rvMarcas);
        rvMarcas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        marcaAdapter = new MarcaAdapter();
        rvMarcas.setAdapter(marcaAdapter);

        // Productos (Grid ya viene definido en XML layoutManager="GridLayoutManager")
        RecyclerView rvProductos = findViewById(R.id.rvProductos);
        productoAdapter = new ProductoAdapter();
        rvProductos.setAdapter(productoAdapter);
    }

    private void setupObservers() {
        viewModel.getCategorias().observe(this, categorias -> {
            if (categorias != null) categoriaAdapter.setCategorias(categorias);
        });

        viewModel.getMarcas().observe(this, marcas -> {
            if (marcas != null) marcaAdapter.setMarcas(marcas);
        });

        viewModel.getProductos().observe(this, productos -> {
            if (productos != null) productoAdapter.setProductos(productos);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
