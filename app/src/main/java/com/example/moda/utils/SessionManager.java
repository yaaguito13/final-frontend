package com.example.moda.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "ModaEatsSession";
    private static final String KEY_USER_ID = "usuario_id";
    
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // Guarda el ID al hacer login exitoso
    public void saveUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // Retorna el ID del usuario (o -1 si no hay sesión activa)
    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public void saveCredentials(String email, String password) {
        editor.putString("saved_email", email);
        editor.putString("saved_password", password);
        editor.apply();
    }

    public void clearCredentials() {
        editor.remove("saved_email");
        editor.remove("saved_password");
        editor.apply();
    }

    public String getSavedEmail() {
        return prefs.getString("saved_email", "");
    }

    public String getSavedPassword() {
        return prefs.getString("saved_password", "");
    }

    // Limpia la sesión al hacer logout
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
