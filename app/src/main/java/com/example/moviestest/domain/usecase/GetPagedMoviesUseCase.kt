package com.example.moviestest.domain.usecase

import com.example.moviestest.domain.repository.MovieRepository
import javax.inject.Inject


class GetPagedMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.pagedMovies()
}