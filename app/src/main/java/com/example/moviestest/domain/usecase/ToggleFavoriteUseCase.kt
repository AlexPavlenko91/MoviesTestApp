package com.example.moviestest.domain.usecase

import com.example.moviestest.domain.repository.MovieRepository
import javax.inject.Inject


class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, isFavorite: Boolean) {
        repository.toggleFavorite(movieId, isFavorite)
    }
}