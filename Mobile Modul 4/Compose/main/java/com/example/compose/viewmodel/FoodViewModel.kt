package com.example.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.compose.data.itemList
import com.example.compose.model.ItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class FoodViewModel(private val username: String) : ViewModel() {
    private val _foodList = MutableStateFlow<List<ItemData>>(emptyList())
    val foodList: StateFlow<List<ItemData>> = _foodList.asStateFlow()

    private val _selectedItem = MutableStateFlow<ItemData?>(null)
    val selectedItem: StateFlow<ItemData?> = _selectedItem.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _foodList.value = itemList
        Timber.d("User $username: Data berhasil dimuat.")
    }

    fun onDetailClicked(item: ItemData) {
        Timber.i("Tombol Detail ditekan")
        Timber.d("Item dipilih: ${item.title}")
        _selectedItem.value = item
    }

    fun onBrowserClicked() {
        Timber.i("Tombol Browser diklik oleh $username")
    }

    fun clearSelection() {
        _selectedItem.value = null
    }
}