package com.example.moda.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moda.FavoritosActivity;
import com.example.moda.HomeActivity;
import com.example.moda.R;

public class NavigationUtils {

    public static final int MENU_INICIO = 0;
    public static final int MENU_FAVORITOS = 1;
    public static final int MENU_CARRITO = 2;
    public static final int MENU_PERFIL = 3;

    public static void setupBottomNav(Activity activity, int selectedMenu) {
        
        LinearLayout navInicio = activity.findViewById(R.id.nav_inicio);
        LinearLayout navFavoritos = activity.findViewById(R.id.nav_favoritos);
        LinearLayout navCarrito = activity.findViewById(R.id.nav_carrito);
        LinearLayout navPerfil = activity.findViewById(R.id.nav_perfil);

        if (navInicio == null) return; // Si no hay bottom nav salir

        // Reset All
        resetTab(activity, R.id.nav_inicio, R.id.nav_icon_inicio, R.id.nav_text_inicio);
        resetTab(activity, R.id.nav_favoritos, R.id.nav_icon_favoritos, R.id.nav_text_favoritos);
        resetTab(activity, R.id.nav_carrito, R.id.nav_icon_carrito, R.id.nav_text_carrito);
        resetTab(activity, R.id.nav_perfil, R.id.nav_icon_perfil, R.id.nav_text_perfil);

        // Highlight Selected
        switch (selectedMenu) {
            case MENU_INICIO:
                highlightTab(activity, R.id.nav_inicio, R.id.nav_icon_inicio, R.id.nav_text_inicio);
                break;
            case MENU_FAVORITOS:
                highlightTab(activity, R.id.nav_favoritos, R.id.nav_icon_favoritos, R.id.nav_text_favoritos);
                break;
            case MENU_CARRITO:
                highlightTab(activity, R.id.nav_carrito, R.id.nav_icon_carrito, R.id.nav_text_carrito);
                break;
            case MENU_PERFIL:
                highlightTab(activity, R.id.nav_perfil, R.id.nav_icon_perfil, R.id.nav_text_perfil);
                break;
        }

        // Helper para navegar con animación
        android.view.View.OnClickListener navListener = v -> {
            Intent intent = null;
            int nextMenu = -1;
            
            if (v.getId() == R.id.nav_inicio && selectedMenu != MENU_INICIO) {
                intent = new Intent(activity, HomeActivity.class);
                nextMenu = MENU_INICIO;
            } else if (v.getId() == R.id.nav_favoritos && selectedMenu != MENU_FAVORITOS) {
                intent = new Intent(activity, FavoritosActivity.class);
                nextMenu = MENU_FAVORITOS;
            } else if (v.getId() == R.id.nav_carrito && selectedMenu != MENU_CARRITO) {
                intent = new Intent(activity, com.example.moda.CarritoActivity.class);
                nextMenu = MENU_CARRITO;
            } else if (v.getId() == R.id.nav_perfil && selectedMenu != MENU_PERFIL) {
                intent = new Intent(activity, com.example.moda.PerfilActivity.class);
                nextMenu = MENU_PERFIL;
            }

            if (intent != null) {
                final Intent finalIntent = intent;
                
                // Forzar UI a animarse en la actividad actual antes de irse
                resetTab(activity, R.id.nav_inicio, R.id.nav_icon_inicio, R.id.nav_text_inicio);
                resetTab(activity, R.id.nav_favoritos, R.id.nav_icon_favoritos, R.id.nav_text_favoritos);
                resetTab(activity, R.id.nav_carrito, R.id.nav_icon_carrito, R.id.nav_text_carrito);
                resetTab(activity, R.id.nav_perfil, R.id.nav_icon_perfil, R.id.nav_text_perfil);

                switch (nextMenu) {
                    case MENU_INICIO: highlightTab(activity, R.id.nav_inicio, R.id.nav_icon_inicio, R.id.nav_text_inicio); break;
                    case MENU_FAVORITOS: highlightTab(activity, R.id.nav_favoritos, R.id.nav_icon_favoritos, R.id.nav_text_favoritos); break;
                    case MENU_CARRITO: highlightTab(activity, R.id.nav_carrito, R.id.nav_icon_carrito, R.id.nav_text_carrito); break;
                    case MENU_PERFIL: highlightTab(activity, R.id.nav_perfil, R.id.nav_icon_perfil, R.id.nav_text_perfil); break;
                }

                // Transición ultra rápida, sin retrasos artificiales, con un fade_in/out estándar de Android
                activity.startActivity(finalIntent);
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };

        navInicio.setOnClickListener(navListener);
        navFavoritos.setOnClickListener(navListener);
        navCarrito.setOnClickListener(navListener);
        navPerfil.setOnClickListener(navListener);
    }

    private static void resetTab(Activity activity, int containerId, int iconId, int textId) {
        LinearLayout container = activity.findViewById(containerId);
        ImageView icon = activity.findViewById(iconId);
        TextView text = activity.findViewById(textId);
        
        if (container != null) {
            android.util.TypedValue outValue = new android.util.TypedValue();
            activity.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
            container.setBackgroundResource(outValue.resourceId);
        }
        if (icon != null) {
            icon.setColorFilter(android.graphics.Color.parseColor("#CCCCCC"));
        }
        if (text != null) {
            text.setVisibility(View.GONE);
        }
    }

    private static void highlightTab(Activity activity, int containerId, int iconId, int textId) {
        LinearLayout container = activity.findViewById(containerId);
        ImageView icon = activity.findViewById(iconId);
        TextView text = activity.findViewById(textId);
        
        if (container != null) {
            container.setBackgroundResource(R.drawable.bg_pill_black);
        }
        if (icon != null) {
            icon.setColorFilter(android.graphics.Color.parseColor("#FFFFFF"));
        }
        if (text != null) {
            text.setVisibility(View.VISIBLE);
        }
    }
}
