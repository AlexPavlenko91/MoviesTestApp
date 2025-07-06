package com.example.moviestest.presentation.screen.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey
import com.example.moviestest.presentation.screen.movies.components.MoviesSection


@Composable
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyPagingItems = viewModel.pagedMovies.collectAsLazyPagingItems()

    when (uiState) {
        is MoviesUiState.Loading -> CircularProgressIndicator()
        is MoviesUiState.Error -> Text("Error")

        is MoviesUiState.Content -> {
            val content = uiState as MoviesUiState.Content

            Column {
                TabRow(selectedTabIndex = content.selectedTab.ordinal) {
                    MoviesTab.entries.forEach { tab ->
                        Tab(
                            selected = content.selectedTab == tab,
                            onClick = { viewModel.selectTab(tab) },
                            text = { Text(tab.name) }
                        )
                    }
                }

                when (content.selectedTab) {
                    MoviesTab.All -> MoviesSection(
                        pagingItems = lazyPagingItems,
                        onFavoriteClick = viewModel::onToggleFavorite
                    )

                    MoviesTab.Favorites -> MoviesSection(
                        grouped = content.favoritesGrouped,
                        onFavoriteClick = viewModel::onToggleFavorite
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMoviesSectionPaging() {
    MoviesSection(
        grouped = mapOf(
            YearMonthKey(2024, 7) to Movie.getMockMovies()
        ),
        onFavoriteClick = {}
    )
}