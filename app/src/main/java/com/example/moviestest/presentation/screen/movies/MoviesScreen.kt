package com.example.moviestest.presentation.screen.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviestest.presentation.screen.movies.components.MoviesAllSection
import com.example.moviestest.presentation.screen.movies.components.MoviesFavoritesSection


@Composable
fun MoviesScreen(viewModel: MoviesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val favoritesGrouped by viewModel.favoritesGrouped.collectAsState()
    val favoritesIds by viewModel.favoriteIds.collectAsState()
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
                    MoviesTab.All -> {
                        MoviesAllSection(
                            pagingItems = lazyPagingItems,
                            favoritesIds = favoritesIds,
                            onFavoriteClick = viewModel::onToggleFavorite,
                        )
                    }
                    MoviesTab.Favorites -> MoviesFavoritesSection(
                        grouped = favoritesGrouped,
                        onFavoriteClick = viewModel::onToggleFavorite
                    )
                }
            }
        }
    }
}