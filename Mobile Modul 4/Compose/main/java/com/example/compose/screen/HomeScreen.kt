package com.example.compose.screen

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.BrowserActivity
import com.example.compose.R
import com.example.compose.model.ItemData
import com.example.compose.viewmodel.FoodViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: FoodViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val foodList by viewModel.foodList.collectAsState()
    val selectedItem by viewModel.selectedItem.collectAsState()

    LaunchedEffect(selectedItem) {
        selectedItem?.let { item ->
            navController.navigate("detail/${item.title}/${item.subtitle}/${item.description}/${item.imagesRes}")
            viewModel.clearSelection()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.menu_title),
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(
                onClick = {
                    navController.navigate("language")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = stringResource(R.string.choose_language)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LazyRow {
                    items(foodList) { item ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(150.dp)
                                .clickable {
                                    viewModel.onDetailClicked(item)
                                },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = item.imagesRes),
                                contentDescription = item.title,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(foodList) { item ->
                        ListItemCard(
                            item = item,
                            onBrowserClick = {
                                viewModel.onBrowserClicked()
                                val intent = Intent(context, BrowserActivity::class.java)
                                context.startActivity(intent)
                            },
                            onDetailClick = {
                                viewModel.onDetailClicked(item)
                            }
                        )
                    }
                }
            }
        } else {
            LazyRow {
                items(foodList) { item ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(150.dp)
                            .clickable {
                                viewModel.onDetailClicked(item)
                            },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.imagesRes),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(foodList) { item ->
                    ListItemCard(
                        item = item,
                        onBrowserClick = {
                            viewModel.onBrowserClicked()
                            val intent = Intent(context, BrowserActivity::class.java)
                            context.startActivity(intent)
                        },
                        onDetailClick = {
                            viewModel.onDetailClicked(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HighlightCard(item: ItemData) {
    Card(
        modifier = Modifier
            .size(220.dp, 150.dp)
            .padding(end = 12.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Image(
            painter = painterResource(item.imagesRes),
            contentDescription = item.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ListItemCard(
    item: ItemData,
    onBrowserClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(item.imagesRes),
                contentDescription = item.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(item.title, style = MaterialTheme.typography.titleMedium)
                Text(item.subtitle, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = onBrowserClick) {
                        Text(stringResource(R.string.open))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onDetailClick) {
                        Text(stringResource(R.string.detail))
                    }
                }
            }
        }
    }
}