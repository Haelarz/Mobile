package com.example.compose.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.R
import com.example.compose.local.LanguagePreferences
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(onBack: () -> Unit) {

    val context = LocalContext.current
    val prefs = LanguagePreferences(context)

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    val activity = context.findActivity()

    fun setLocale(code: String) {
        val locale = Locale(code)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        activity?.let {
            it.resources.updateConfiguration(
                config,
                it.resources.displayMetrics
            )
            it.recreate()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.choose_language)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    prefs.saveLanguage("in")
                    setLocale("in")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.language_indonesia))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    prefs.saveLanguage("en")
                    setLocale("en")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.language_english))
            }
        }
    }
}
