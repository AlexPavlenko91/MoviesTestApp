package com.example.moviestest.domain.repository

import com.example.moviestest.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface FavoritesRepository {
    fun getFavorites():  Flow<List<Movie>>
    suspend fun addToFavorites(movie: Movie)
    suspend fun removeFromFavorites(movieId: Int)
    suspend fun isFavorite(movieId: Int): Boolean
}