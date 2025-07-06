package com.example.moviestest.domain.model


data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String,
    val isFavorite: Boolean
)