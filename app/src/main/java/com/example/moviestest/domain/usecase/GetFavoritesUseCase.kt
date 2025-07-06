package com.example.moviestest.domain.usecase

import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> = repository.getFavorites()
}