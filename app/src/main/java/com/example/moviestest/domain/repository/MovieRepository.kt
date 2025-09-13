package com.example.moviestest.domain.repository

import androidx.paging.PagingData
import com.example.moviestest.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun pagedMovies(): Flow<PagingData<Movie>>
    suspend fun getCachedMovies(): List<Movie>
    suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean)
    suspend fun cacheMovies(movies: List<Movie>)
}