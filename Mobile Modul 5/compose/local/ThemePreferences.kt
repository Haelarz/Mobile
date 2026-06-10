package com.example.compose.local

import android.content.Context
import android.content.SharedPreferences

class ThemePreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    fun saveTheme(isDark: Boolean) {
        prefs.edit().putBoolean("IS_DARK", isDark).apply()
    }

    fun isDarkTheme(systemDefault: Boolean): Boolean {
        return prefs.getBoolean("IS_DARK", systemDefault)
    }
}