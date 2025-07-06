package com.example.moviestest.domain.repository

import com.example.moviestest.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getRemoteMovies(page: Int): List<Movie>
    suspend fun getCachedMovies(): List<Movie>
    suspend fun getFavorites(): Flow<List<Movie>>
    suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean)
    suspend fun cacheMovies(movies: List<Movie>)
}