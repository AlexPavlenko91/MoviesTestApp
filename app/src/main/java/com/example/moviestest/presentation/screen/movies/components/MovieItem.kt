package com.example.moviestest.presentation.screen.movies.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviestest.R
import com.example.moviestest.domain.model.Movie


@Composable
fun MovieItem(
    movie: Movie, isFavoriteTab: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Movie) -> Unit
) {
    val favoriteContentDescRes = if (isFavoriteTab) {
        R.string.remove_from_favorites
    } else {
        R.string.add_to_favorites
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(stringResource(R.string.rating_star, movie.voteAverage))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(movie.title, fontWeight = FontWeight.Bold)
            Text(movie.overview, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Row {
                IconButton(onClick = { onFavoriteClick(movie) }) {
                    Icon(
                        imageVector = if (isFavoriteTab) Icons.Default.Delete else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(favoriteContentDescRes)
                    )
                }
                IconButton(onClick = { /* share */ }) {
                    Icon(Icons.Default.Share, contentDescription = stringResource(R.string.share))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieItem() {
    MovieItem(
        movie = Movie.getMockMovies()[0],
        isFavoriteTab = false,
        onFavoriteClick = {}
    )
}