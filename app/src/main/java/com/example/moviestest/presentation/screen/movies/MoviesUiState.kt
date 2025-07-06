package com.example.moviestest.presentation.screen.movies

import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey


sealed class MoviesUiState {
    object Loading : MoviesUiState()

    data class Content(
        val selectedTab: MoviesTab,
        val favoritesGrouped: Map<YearMonthKey, List<Movie>>
    ) : MoviesUiState()

    data class Error(val message: String) : MoviesUiState()
}