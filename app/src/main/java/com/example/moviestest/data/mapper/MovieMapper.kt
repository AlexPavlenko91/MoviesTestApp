package com.example.moviestest.data.mapper

import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.remote.dto.MovieDto
import com.example.moviestest.domain.model.Movie


const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.dtoToDomain(isFavorite: Boolean = false): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterPath?.let { "$TMDB_IMAGE_BASE_URL$it" },
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        overview = overview,
        isFavorite = isFavorite
    )
}

fun MovieDto.dtoToEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterUrl = posterPath?.let { "$TMDB_IMAGE_BASE_URL$it" },
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount,
        overview = overview
    )
}

fun Movie.domainToEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        posterUrl = posterUrl,
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage,
        voteCount = voteCount,
        overview = overview,
        isFavorite = isFavorite
    )
}


fun MovieEntity.entityToDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterUrl,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        overview = overview,
        isFavorite = isFavorite
    )
}