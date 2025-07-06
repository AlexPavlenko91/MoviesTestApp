package com.example.moviestest.domain.model

import com.example.moviestest.data.mapper.TMDB_IMAGE_BASE_URL


data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String,
    val isFavorite: Boolean
) {

    companion object {
        fun getMockMovies(): List<Movie> {
            return listOf(
                Movie(
                    id = 2,
                    title = "Domain Movie",
                    posterUrl = "$TMDB_IMAGE_BASE_URL/2VUmvqsHb6cEtdfscEA6fqqVzLg.jpg",
                    releaseDate = "2025-02-01",
                    voteAverage = 7.5,
                    voteCount = 900,
                    overview = "Domain movie overview.",
                    isFavorite = true
                ),
                Movie(
                    id = 541671,
                    title = "Domain Movie",
                    posterUrl = "$TMDB_IMAGE_BASE_URL/hqcexYHbiTBfDIdDWxrxPtVndBX.jpg",
                    releaseDate = "2025-01-01",
                    voteAverage = 7.7,
                    voteCount = 1000,
                    overview = "Domain movie overview. 2",
                    isFavorite = true
                )
            )
        }
    }
}