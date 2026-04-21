package com.example.moda;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BuscarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        com.example.moda.utils.NavigationUtils.setupBottomNav(this, com.example.moda.utils.NavigationUtils.MENU_INICIO);
    }
}
