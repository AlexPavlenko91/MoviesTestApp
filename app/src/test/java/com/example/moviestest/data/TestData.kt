package com.example.moviestest.data

import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.mapper.TMDB_IMAGE_BASE_URL
import com.example.moviestest.data.remote.dto.MovieDto
import com.example.moviestest.domain.model.Movie

val mockDto1 = MovieDto(
    id = 541671,
    title = "Ballerina",
    posterPath = "/2VUmvqsHb6cEtdfscEA6fqqVzLg.jpg",
    releaseDate = "2025-06-04",
    voteAverage = 7.326,
    voteCount = 642,
    overview = "Taking place during the events of John Wick: Chapter 3 – Parabellum, Eve Macarro begins her training in the assassin traditions of the Ruska Roma."
)

val mockEntity1 = MovieEntity(
    id = 541671,
    title = "Ballerina",
    posterUrl = "$TMDB_IMAGE_BASE_URL/2VUmvqsHb6cEtdfscEA6fqqVzLg.jpg",
    releaseDate = "2025-06-04",
    voteAverage = 7.326,
    voteCount = 642,
    overview = "Taking place during the events of John Wick: Chapter 3 – Parabellum, Eve Macarro begins her training in the assassin traditions of the Ruska Roma.",
    isFavorite = false
)

val mockDomain1 = Movie(
    id = 2,
    title = "Domain Movie",
    posterUrl = "$TMDB_IMAGE_BASE_URL/2VUmvqsHb6cEtdfscEA6fqqVzLg.jpg",
    releaseDate = "2025-02-01",
    voteAverage = 7.5,
    voteCount = 900,
    overview = "Domain movie overview.",
    isFavorite = true
)
val mockDomain2 = Movie(
    id = 541671,
    title = "Domain Movie",
    posterUrl = "$TMDB_IMAGE_BASE_URL/hqcexYHbiTBfDIdDWxrxPtVndBX.jpg",
    releaseDate = "2025-01-01",
    voteAverage = 7.7,
    voteCount = 1000,
    overview = "Domain movie overview. 2",
    isFavorite = true
)
val mockDomain2024 = Movie(
    id = 2024,
    title = "Domain Movie",
    posterUrl = "$TMDB_IMAGE_BASE_URL/2VUmvqsHb6cEtdfscEA6fqqVzLg.jpg",
    releaseDate = "2024-01-01",
    voteAverage = 7.5,
    voteCount = 900,
    overview = "Domain movie overview.",
    isFavorite = true
)
val mockDomain2023 = Movie(
    id = 2023,
    title = "Domain Movie",
    posterUrl = "$TMDB_IMAGE_BASE_URL/hqcexYHbiTBfDIdDWxrxPtVndBX.jpg",
    releaseDate = "2023-05-01",
    voteAverage = 7.7,
    voteCount = 1000,
    overview = "Domain movie overview. 2",
    isFavorite = true
)