package com.example.compose.repository

import com.example.compose.local.MovieDao
import com.example.compose.local.MovieEntity
import com.example.compose.model.Movie
import com.example.compose.network.ApiResponse
import com.example.compose.network.TmdbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieRepository(
    private val apiService: TmdbApiService,
    private val movieDao: MovieDao
) {
    fun getPopularMovies(apiKey: String): Flow<ApiResponse<List<Movie>>> = flow {
        emit(ApiResponse.Loading)

        val localData = movieDao.getAllMovies().first()

        if (localData.isNotEmpty()) {
            val mappedToDomain = localData.map {
                Movie(it.id, it.title, it.overview, it.posterPath)
            }
            emit(ApiResponse.Success(mappedToDomain))
        }

        try {
            val response = apiService.getPopularMovies(apiKey)
            val networkMovies = response.results

            val entities = networkMovies.map {
                MovieEntity(it.id, it.title, it.overview, it.posterPath)
            }

            movieDao.deleteAllMovies()
            movieDao.insertMovies(entities)

            emit(ApiResponse.Success(networkMovies))

        } catch (e: HttpException) {
            if (localData.isEmpty()) emit(ApiResponse.Error("Terjadi kesalahan server."))
        } catch (e: IOException) {
            if (localData.isEmpty()) emit(ApiResponse.Error("Gagal terhubung ke internet."))
        }
    }
}