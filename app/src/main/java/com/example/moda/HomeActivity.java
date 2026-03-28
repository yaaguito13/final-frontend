package com.example.moda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moda.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SessionManager sessionManager = new SessionManager(this);
        Button btnLogout = findViewById(R.id.btnLogout);
        TextView tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);

        int userId = sessionManager.getUserId();
        tvWelcomeMessage.setText("¡Bienvenido a ModaEats!\nTu ID es: " + userId);

        btnLogout.setOnClickListener(v -> {
            sessionManager.clearSession();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            // Evitar que el usuario vuelva a Home usando el botón Atrás tras desloguearse
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
