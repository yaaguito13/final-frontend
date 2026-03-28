package com.example.moda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moda.utils.SessionManager;
import com.example.moda.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private SessionManager sessionManager;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        
        // Inicializar ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Resaltar "Regístrate" (negrita, subrayado y color negro)
        tvRegister.setText(android.text.Html.fromHtml("¿NO TIENES CUENTA? <b><u><font color='#000000'>Regístrate</font></u></b>", android.text.Html.FROM_HTML_MODE_LEGACY));

        // Si ya hay sesión iniciada, ir directo a Home
        if (sessionManager.getUserId() != -1) {
            goToHome();
        }

        // Observadores
        setupObservers();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            loginViewModel.login(email, password);
        });
        
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void setupObservers() {
        loginViewModel.getLoginSuccess().observe(this, userId -> {
            sessionManager.saveUserId(userId);
            Toast.makeText(this, "¡Login exitoso!", Toast.LENGTH_SHORT).show();
            goToHome();
        });

        loginViewModel.getLoginError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // destroy login activity
    }
}
