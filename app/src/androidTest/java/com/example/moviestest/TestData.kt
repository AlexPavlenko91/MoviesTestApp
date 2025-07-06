package com.example.moviestest

import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.mapper.TMDB_IMAGE_BASE_URL


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

val mockEntity2 = MovieEntity(
    id = 986056,
    title = "Thunderbolts*",
    posterUrl = "$TMDB_IMAGE_BASE_URL/hqcexYHbiTBfDIdDWxrxPtVndBX.jpg",
    releaseDate = "2025-04-30",
    voteAverage = 7.435,
    voteCount = 1517,
    overview = "After finding themselves ensnared in a death trap, seven disillusioned castoffs must embark on a dangerous mission that will force them to confront the darkest corners of their pasts.",
    isFavorite = true
)