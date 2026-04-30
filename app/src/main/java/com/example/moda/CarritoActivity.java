package com.example.moda;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moda.api.ApiClient;
import com.example.moda.api.ApiService;
import com.example.moda.models.CarritoItem;
import com.example.moda.models.CarritoResponse;
import com.example.moda.models.UpdateQuantityRequest;
import com.example.moda.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoActivity extends AppCompatActivity {

    private LinearLayout emptyStateCarrito;
    private RelativeLayout filledStateCarrito;
    private RecyclerView rvCarrito;
    private TextView tvTotalGeneral, tvSubtotalTotal;
    private Button btnEmptyComprar, btnCheckout;

    private ApiService apiService;
    private CarritoAdapter adapter;
    private int sessionUserId = 1;

    private CarritoAdapter.OnCartActionListener cartListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        NavigationUtils.setupBottomNav(this, NavigationUtils.MENU_CARRITO);

        emptyStateCarrito = findViewById(R.id.emptyStateCarrito);
        filledStateCarrito = findViewById(R.id.filledStateCarrito);
        rvCarrito = findViewById(R.id.rvCarrito);
        tvTotalGeneral = findViewById(R.id.tvTotalGeneral);
        tvSubtotalTotal = findViewById(R.id.tvSubtotalTotal);
        btnEmptyComprar = findViewById(R.id.btnEmptyComprar);
        btnCheckout = findViewById(R.id.btnCheckout);
        TextView btnClearCart = findViewById(R.id.btnClearCart);

        apiService = ApiClient.getClient().create(ApiService.class);

        com.example.moda.utils.SessionManager sessionManager = new com.example.moda.utils.SessionManager(this);
        sessionUserId = sessionManager.getUserId();

        rvCarrito.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CarritoAdapter();
        rvCarrito.setAdapter(adapter);

        // Actions
        btnClearCart.setOnClickListener(v -> {
            if (adapter.getItemCount() > 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Vaciar Carrito")
                        .setMessage("¿Estás seguro de que quieres eliminar TODOS los productos?")
                        .setPositiveButton("Sí, vaciar", (dialog, which) -> vaciarCarrito())
                        .setNegativeButton("No", null)
                        .show();
            } else {
                Toast.makeText(this, "El carrito ya está vacío", Toast.LENGTH_SHORT).show();
            }
        });

        btnEmptyComprar.setOnClickListener(v -> {
            // Ir a Inicio simulando clic en la tab
            findViewById(R.id.nav_inicio).performClick();
        });

        btnCheckout.setOnClickListener(v -> {
            Toast.makeText(this, "Hacia pasarela de pago...", Toast.LENGTH_SHORT).show();
        });

        cartListener = new CarritoAdapter.OnCartActionListener() {
            @Override
            public void onIncreaseQuantity(CarritoItem item, int position) {
                updateItemQuantity(item.getId(), item.getCantidad() + 1, position);
            }

            @Override
            public void onDecreaseQuantity(CarritoItem item, int position) {
                if (item.getCantidad() > 1) {
                    updateItemQuantity(item.getId(), item.getCantidad() - 1, position);
                } else {
                    confirmarEliminacion(item.getId(), position);
                }
            }

            @Override
            public void onDeleteItem(CarritoItem item, int position) {
                confirmarEliminacion(item.getId(), position);
            }
        };

        adapter.setCarrito(new ArrayList<>(), cartListener);

        cargarCarrito();
    }

    private void updateUI() {
        List<CarritoItem> items = adapter.getItems();
        Log.d("CARRITO_TOTAL", "Actualizando UI con " + (items != null ? items.size() : 0) + " items");
        
        if (items == null || items.isEmpty()) {
            emptyStateCarrito.setVisibility(View.VISIBLE);
            filledStateCarrito.setVisibility(View.GONE);
        } else {
            emptyStateCarrito.setVisibility(View.GONE);
            filledStateCarrito.setVisibility(View.VISIBLE);
            
            double sumTotal = 0.0;
            for (CarritoItem item : items) {
                double sub = item.getSubtotal();
                Log.d("CARRITO_TOTAL", "Item: " + (item.getNombre() != null ? item.getNombre() : "null") + " | Subtotal: " + sub);
                sumTotal += sub;
            }
            
            tvSubtotalTotal.setText(String.format("%.2f €", sumTotal));
            tvTotalGeneral.setText(String.format("%.2f €", sumTotal));
            Log.d("CARRITO_TOTAL", "Total calculado final: " + sumTotal);
        }
    }

    private void cargarCarrito() {
        Log.d("CARRITO", "Cargando carrito para usuario: " + sessionUserId);
        if (sessionUserId == -1) {
            adapter.setCarrito(new ArrayList<>(), cartListener);
            updateUI();
            return;
        }

        apiService.getCarrito(sessionUserId).enqueue(new Callback<CarritoResponse>() {
            @Override
            public void onResponse(Call<CarritoResponse> call, Response<CarritoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CarritoItem> carritoItems = response.body().getCarrito();
                    if (carritoItems != null) {
                        adapter.setCarrito(carritoItems, cartListener);
                    } else {
                        // Fallback in case serialization returns null
                        adapter.setCarrito(new ArrayList<>(), cartListener);
                    }
                } else {
                    adapter.setCarrito(new ArrayList<>(), cartListener);
                    // Log out failure info
                    Log.e("CARRITO", "Code: " + response.code());
                }
                updateUI();
            }

            @Override
            public void onFailure(Call<CarritoResponse> call, Throwable t) {
                adapter.setCarrito(new ArrayList<>(), cartListener);
                updateUI();
                Toast.makeText(CarritoActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateItemQuantity(int itemId, int newQuantity, int position) {
        UpdateQuantityRequest request = new UpdateQuantityRequest(newQuantity);
        apiService.updateCarrito(itemId, request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Update Local UI instantly
                    CarritoItem item = adapter.getItems().get(position);
                    item.setCantidad(newQuantity);
                    adapter.updateItem(position, item);
                    updateUI();
                } else {
                    Toast.makeText(CarritoActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CarritoActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmarEliminacion(int itemId, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar del Carrito")
                .setMessage("¿Estás seguro de que quieres quitar este producto de tu cesta?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    apiService.deleteCarrito(itemId).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                adapter.removeItem(position);
                                updateUI();
                                Toast.makeText(CarritoActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error " + response.code();
                                    Log.e("CARRITO_DELETE", "Error: " + errorBody);
                                    Toast.makeText(CarritoActivity.this, "Error backend: " + response.code(), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(CarritoActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
 
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("CARRITO_DELETE", "Failure: " + t.getMessage());
                            Toast.makeText(CarritoActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }
 
    private void vaciarCarrito() {
        List<CarritoItem> items = new ArrayList<>(adapter.getItems());
        if (items.isEmpty()) return;

        Toast.makeText(this, "Vaciando carrito...", Toast.LENGTH_SHORT).show();
        for (CarritoItem item : items) {
            apiService.deleteCarrito(item.getId()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // Ignoramos errores individuales para seguir vaciando, 
                    // o cargamos de nuevo al final
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {}
            });
        }
        // Limpiamos localmente para feedback instantáneo
        adapter.setCarrito(new ArrayList<>(), cartListener);
        updateUI();
    }
}
