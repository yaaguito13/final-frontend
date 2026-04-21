package com.example.moda;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.CarritoRequest;
import com.example.moda.models.FavoritoRequest;
import com.example.moda.models.Producto;
import com.example.moda.utils.SessionManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView ivProductImage, ivBrandLogo;
    private TextView tvBrandName, tvProductRating, tvProductName, tvProductPrice, tvProductDescription;
    private ImageButton btnBack, btnShare, btnFavorite;
    private LinearLayout btnAddToCart, btnBuyNow;
    
    // Tallas
    private TextView talla40, talla41, talla42, talla43, talla44;
    // Colores
    private TextView colorNegro, colorBlanco, colorGris, colorAzul;

    private int productoId = -1;
    private int usuarioId = -1;
    private SessionManager sessionManager;
    private ApiService apiService;

    private String selectedTalla = "";
    private String selectedColor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        sessionManager = new SessionManager(this);
        usuarioId = sessionManager.getUserId();
        apiService = ApiClient.getClient().create(ApiService.class);

        productoId = getIntent().getIntExtra("producto_id", -1);

        initViews();
        setupListeners();
        setupSelectors();

        if (productoId != -1) {
            loadProductDetails();
        } else {
            Toast.makeText(this, "Error al cargar producto", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        ivProductImage = findViewById(R.id.ivProductImageDetail);
        ivBrandLogo = findViewById(R.id.ivBrandLogoDetail);
        tvBrandName = findViewById(R.id.tvBrandNameDetail);
        tvProductRating = findViewById(R.id.tvProductRatingDetail);
        tvProductName = findViewById(R.id.tvProductNameDetail);
        tvProductPrice = findViewById(R.id.tvProductPriceDetail);
        tvProductDescription = findViewById(R.id.tvProductDescriptionDetail);

        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);

        talla40 = findViewById(R.id.talla_40);
        talla41 = findViewById(R.id.talla_41);
        talla42 = findViewById(R.id.talla_42);
        talla43 = findViewById(R.id.talla_43);
        talla44 = findViewById(R.id.talla_44);

        colorNegro = findViewById(R.id.color_negro);
        colorBlanco = findViewById(R.id.color_blanco);
        colorGris = findViewById(R.id.color_gris);
        colorAzul = findViewById(R.id.color_azul);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        
        btnShare.setOnClickListener(v -> {
            Toast.makeText(this, "Compartir presionado", Toast.LENGTH_SHORT).show();
        });

        btnFavorite.setOnClickListener(v -> {
            if (usuarioId == -1) {
                Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
                return;
            }
            añadirAFavoritos();
        });

        btnAddToCart.setOnClickListener(v -> actionCartOrBuy());
        btnBuyNow.setOnClickListener(v -> actionCartOrBuy());
    }

    private void actionCartOrBuy() {
        if (usuarioId == -1) {
            Toast.makeText(this, "Debes iniciar sesión para comprar", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedTalla.isEmpty()) {
            Toast.makeText(this, "Por favor selecciona una talla", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedColor.isEmpty()) {
            Toast.makeText(this, "Por favor selecciona un color", Toast.LENGTH_SHORT).show();
            return;
        }
        añadirAlCarrito();
    }

    private void setupSelectors() {
        TextView[] tallas = {talla40, talla41, talla42, talla43, talla44};
        for (TextView t : tallas) {
            t.setOnClickListener(v -> {
                for (TextView otherT : tallas) {
                    otherT.setBackgroundResource(R.drawable.bg_selectable_unselected);
                    otherT.setTextColor(android.graphics.Color.parseColor("#000000"));
                }
                t.setBackgroundResource(R.drawable.bg_selectable_selected);
                t.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                selectedTalla = t.getText().toString();
            });
        }

        TextView[] colores = {colorNegro, colorBlanco, colorGris, colorAzul};
        for (TextView c : colores) {
            c.setOnClickListener(v -> {
                for (TextView otherC : colores) {
                    otherC.setBackgroundResource(R.drawable.bg_selectable_unselected);
                    otherC.setTextColor(android.graphics.Color.parseColor("#000000"));
                }
                c.setBackgroundResource(R.drawable.bg_selectable_selected);
                c.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                selectedColor = c.getText().toString();
            });
        }
    }

    private void loadProductDetails() {
        apiService.getProducto(productoId).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Producto p = response.body();
                    tvProductName.setText(p.getNombre());
                    tvProductPrice.setText(String.format("%.2f €", p.getPrecio()));
                    tvBrandName.setText(p.getMarcaNombre());
                    tvProductRating.setText(String.valueOf(p.getRating()));
                    
                    if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
                        tvProductDescription.setText(p.getDescripcion());
                    } else {
                        tvProductDescription.setText("Sin descripción disponible.");
                    }

                    String imgUrl = p.getImagenUrl();
                    if (imgUrl != null) {
                        if (imgUrl.contains("127.0.0.1") || imgUrl.contains("localhost")) {
                            imgUrl = imgUrl.replace("127.0.0.1", "10.0.2.2").replace("localhost", "10.0.2.2");
                        }
                        Glide.with(ProductDetailActivity.this)
                                .load(imgUrl)
                                .placeholder(android.R.drawable.ic_menu_gallery)
                                .into(ivProductImage);
                    }
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Error al obtener info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void añadirAFavoritos() {
        FavoritoRequest req = new FavoritoRequest(usuarioId, productoId);
        apiService.addFavorito(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void añadirAlCarrito() {
        CarritoRequest req = new CarritoRequest(usuarioId, productoId, 1, selectedTalla, selectedColor);
        apiService.addCarrito(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, "Añadido al carrito con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Error al añadir al carrito", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
