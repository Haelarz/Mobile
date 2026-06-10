package com.example.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.model.Movie
import com.example.compose.network.ApiResponse
import com.example.compose.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val repository: MovieRepository,
    private val apiKey: String,
) : ViewModel() {

    private val _movieState = MutableStateFlow<ApiResponse<List<Movie>>>(ApiResponse.Loading)
    val movieState: StateFlow<ApiResponse<List<Movie>>> = _movieState.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        fetchMovies()
    }

    init {
        fetchMovies()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            repository.getPopularMovies(apiKey).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _movieState.value = ApiResponse.Loading
                        Timber.d("Sedang memuat data film...")
                    }
                    is ApiResponse.Success -> {
                        _movieState.value = ApiResponse.Success(response.data)
                        Timber.d("Data berhasil dimuat.")
                    }
                    is ApiResponse.Error -> {
                        _movieState.value = ApiResponse.Error(response.errorMessage)
                        Timber.e("Error: ${response.errorMessage}")
                    }
                }
            }
        }
    }

    fun onDetailClicked(movie: Movie) {
        Timber.i("Tombol Detail ditekan: ${movie.title}")
        _selectedMovie.value = movie
    }

    fun clearSelection() {
        _selectedMovie.value = null
    }
}