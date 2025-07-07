package com.example.moviestest.presentation.utils

import android.content.Context
import android.content.Intent
import com.example.moviestest.R
import com.example.moviestest.domain.model.Movie


fun shareMovie(context: Context, movie: Movie) {
    val resources = context.resources
    val movieUrl = "https://www.themoviedb.org/movie/${movie.id}"
    val shareText = resources.getString(
        R.string.share_movie_text_with_link,
        movie.title,
        movie.overview,
        movieUrl
    )
    val shareSubject = resources.getString(R.string.share_movie_subject)
    val chooserTitle = resources.getString(R.string.share_movie_chooser_title)

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        putExtra(Intent.EXTRA_SUBJECT, shareSubject)
    }
    context.startActivity(Intent.createChooser(shareIntent, chooserTitle))
}