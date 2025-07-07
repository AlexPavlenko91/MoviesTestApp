package com.example.moviestest.domain.repository

import com.example.moviestest.domain.model.Movie


interface MovieRepository {
    suspend fun getCachedMovies(): List<Movie>
    suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean)
    suspend fun cacheMovies(movies: List<Movie>)
}