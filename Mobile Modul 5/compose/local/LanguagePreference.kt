package com.example.compose.local

import android.content.Context
import android.content.SharedPreferences

class LanguagePreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveLanguage(languageCode: String) {
        prefs.edit().putString("LANGUAGE", languageCode).apply()
    }

    fun getLanguage(): String {
        return prefs.getString("LANGUAGE", "en") ?: "en" // Default ke bahasa Inggris
    }
}