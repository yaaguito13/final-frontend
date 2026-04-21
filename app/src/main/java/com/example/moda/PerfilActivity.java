package com.example.moda;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        com.example.moda.utils.NavigationUtils.setupBottomNav(this, com.example.moda.utils.NavigationUtils.MENU_PERFIL);
    }
}
