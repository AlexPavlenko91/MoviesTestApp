package com.example.moviestest.presentation.screen.movies.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey


@Composable
fun MoviesSection(
    pagingItems: LazyPagingItems<Movie>? = null,
    grouped: Map<YearMonthKey, List<Movie>>? = null,
    onFavoriteClick: (Movie) -> Unit
) {
    LazyColumn {
        when {
            pagingItems != null -> {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let { movie ->
                        MovieItem(
                            movie = movie, isFavoriteTab = false,
                            onFavoriteClick = onFavoriteClick,
                        )
                    }
                }
            }

            grouped != null -> {
                grouped.forEach { (key, movies) ->
                    item {
                        Text(
                            "${key.month}/${key.year}",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    items(movies.size) { index ->
                        MovieItem(
                            movie = movies[index], isFavoriteTab = true,
                            onFavoriteClick = onFavoriteClick,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMoviesSectionGrouped() {
    MoviesSection(
        grouped = mapOf(
            YearMonthKey(2024, 7) to Movie.getMockMovies()
        ),
        onFavoriteClick = {}
    )
}