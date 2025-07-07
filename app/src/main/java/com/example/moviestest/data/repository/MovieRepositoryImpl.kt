package com.example.moviestest.data.repository

import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.mapper.domainToEntity
import com.example.moviestest.data.mapper.entityToDomain
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.repository.MovieRepository


class MovieRepositoryImpl(
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun getCachedMovies(): List<Movie> {
        return dao.getAllMovies().map { it.entityToDomain() }
    }

    override suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        dao.updateFavorite(movieId, isFavorite)
    }

    override suspend fun cacheMovies(movies: List<Movie>) {
        dao.insertAll(movies.map { it.domainToEntity() })
    }
}
