package com.example.moviestest.data.remote.dto

data class DiscoverMovieParams(
    val page: Int = 1,
    val sortBy: String = "primary_release_date.desc",
    val voteAverageMin: Double = 7.0,
    val voteCountMin: Int = 100,
    val includeAdult: Boolean = false,
    val includeVideo: Boolean = false,
    val language: String = "en-US"
)