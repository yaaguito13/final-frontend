package com.example.moda;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moda.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etConfirm;
    private Button btnRegister;
    private ImageView ivBack;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        ivBack = findViewById(R.id.ivBack);

        // Termina la activity y vuelve atrás
        ivBack.setOnClickListener(v -> finish()); 

        setupObservers();

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirm = etConfirm.getText().toString().trim();
            registerViewModel.register(name, email, password, confirm);
        });
    }

    private void setupObservers() {
        registerViewModel.getRegisterSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(RegisterActivity.this, "Registro completado exitosamente", Toast.LENGTH_SHORT).show();
                finish(); // volver al login
            }
        });

        registerViewModel.getRegisterError().observe(this, error -> {
            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
        });
    }
}
