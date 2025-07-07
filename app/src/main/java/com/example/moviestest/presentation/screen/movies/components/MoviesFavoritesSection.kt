package com.example.moviestest.presentation.screen.movies.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey


@Composable
fun MoviesFavoritesSection(
    grouped: Map<YearMonthKey, List<Movie>>,
    onFavoriteClick: (Movie) -> Unit
) {
    LazyColumn {
        grouped.forEach { (key, movies) ->
            item {
                Text("${key.month}/${key.year}")
            }
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    isFavoriteTab = true,
                    onFavoriteClick = onFavoriteClick,
                )
            }
        }
    }
}