package com.example.compose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect // Ditambahkan untuk logging saat pindah halaman
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import timber.log.Timber // Library logging [cite: 318, 852]

@Composable
fun DetailScreen(
    title: String,
    subtitle: String,
    description: String,
    imageRes: Int
) {
    LaunchedEffect(Unit) {
        Timber.i("Membuka Detail: $title, Deskripsi: $description")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(if (imageRes != 0) imageRes else com.example.compose.R.drawable.food1),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = subtitle)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = description)
    }
}