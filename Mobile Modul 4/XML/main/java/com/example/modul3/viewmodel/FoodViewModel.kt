package com.example.modul3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.modul3.R
import com.example.modul3.model.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class FoodViewModel(private val username: String) : ViewModel() {
    private val _foodList = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodList: StateFlow<List<FoodItem>> = _foodList.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        val data = listOf(
            FoodItem(1, "SMP", "Sarapan", "Enak", R.drawable.mbg1, "https://bgn.go.id/"),
            FoodItem(2, "SD", "Sarapan", "Sehat", R.drawable.mbg2, "https://bgn.go.id/"),
            FoodItem(3, "SD", "Makan Siang", "Fresh", R.drawable.mbg3, "https://bgn.go.id/"),
            FoodItem(4, "SMA", "Makan Malam", "Baik", R.drawable.mbg4, "https://bgn.go.id/"),
            FoodItem(5, "SMP", "Makan Siang", "Pagi", R.drawable.mbg5, "https://bgn.go.id/")
        )
        _foodList.value = data

        Timber.d("User: $username - Data makanan berhasil dimuat. Jumlah item: ${data.size}")
    }
}