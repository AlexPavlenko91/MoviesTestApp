package com.example.moviestest.domain.usecase

import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.repository.FavoritesRepository
import javax.inject.Inject


class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(movie: Movie, isFavorite: Boolean) {
        if (isFavorite) {
            repository.removeFromFavorites(movie.id)
        } else {
            repository.addToFavorites(movie)
        }
    }
}