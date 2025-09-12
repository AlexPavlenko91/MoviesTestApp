package com.example.moviestest.presentation.screen.movies.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.model.YearMonthKey


@Composable
fun MoviesAllSection(
    pagingItems: LazyPagingItems<Movie>,
    favoritesIds: Set<Int>,
    onFavoriteClick: (Movie) -> Unit
) {
    val listState = rememberSaveable("all_list", saver = LazyListState.Saver) {
        LazyListState()
    }

    LazyColumn(state = listState) {
        items(
            count = pagingItems.itemCount,
            key = { index -> pagingItems.peek(index)?.id ?: "placeholder_$index" },
            contentType = { "movie" }
        ) { index ->
            val movie = pagingItems[index] ?: return@items

            val currKey = parseYearMonth(movie.releaseDate)
            val prevRelease = if (index > 0) pagingItems.peek(index - 1)?.releaseDate else null
            val prevKey = prevRelease?.let(::parseYearMonth)

            val needHeader = currKey != null && (index == 0 || currKey != prevKey)

            Column {
                if (needHeader) {
                    Text(
                        "${currKey?.month}/${currKey?.year}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                MovieItem(
                    movie = movie,
                    isFavorite = favoritesIds.contains(movie.id),
                    isFavoriteTab = false,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }

        // load states
        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> item { LoadingBlock() }
                loadState.append  is LoadState.Loading -> item { LoadingMoreBlock() }
                loadState.refresh is LoadState.Error   -> {
                    val err = (loadState.refresh as LoadState.Error).error
                    item { ErrorBlock(throwable = err, onRetry = ::retry) }
                }
                loadState.append  is LoadState.Error   -> {
                    val err = (loadState.append as LoadState.Error).error
                    item { ErrorMoreBlock(throwable = err, onRetry = ::retry) }
                }
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