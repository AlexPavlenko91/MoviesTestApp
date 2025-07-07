package com.example.moviestest.presentation.screen.movies.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey


@Composable
fun MoviesAllSection(
    pagingItems: LazyPagingItems<Movie>,
    favoritesIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit
) {
    var lastHeader: YearMonthKey? by remember { mutableStateOf(null) }
    LazyColumn {
        items(pagingItems.itemCount) { index ->
            val movie = pagingItems[index] ?: return@items
            val key = parseYearMonth(movie.releaseDate)
            val needHeader = key != null && key != lastHeader
            Column {
                if (needHeader && key != null) {
                    lastHeader = key
                    Text(
                        "${key.month}/${key.year}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                MovieItem(
                    movie = movie.copy(isFavorite = favoritesIds.contains(movie.id)),
                    isFavoriteTab = false,
                    onFavoriteClick = onFavoriteClick,
                )
            }
        }
    }
}



fun parseYearMonth(date: String?): YearMonthKey? {

    if (date.isNullOrBlank()) return null
    return try {
        val parts = date.split("-")
        YearMonthKey(parts[0].toInt(), parts[1].toInt())
    } catch (e: Exception) {
        null
    }
}