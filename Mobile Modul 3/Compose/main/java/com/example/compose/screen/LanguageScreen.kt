package com.example.compose.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.R
import java.util.Locale

@Composable
fun LanguageScreen() {

    val context = LocalContext.current
    
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.choose_language),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { setLocale("in") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.language_indonesia))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { setLocale("en") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.language_english))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LanguageScreenPreview() {
    LanguageScreen()
}
