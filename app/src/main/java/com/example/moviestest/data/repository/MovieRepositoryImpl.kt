package com.example.moviestest.data.repository

import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.mapper.domainToEntity
import com.example.moviestest.data.mapper.dtoToDomain
import com.example.moviestest.data.mapper.entityToDomain
import com.example.moviestest.data.mapper.dtoToEntity
import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.data.remote.dto.DiscoverMovieParams
import com.example.moviestest.data.remote.extension.discoverMoviesWithParams
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun getRemoteMovies(page: Int): List<Movie> {
        val response = api.discoverMoviesWithParams(DiscoverMovieParams(page = page))
        val entities = response.results.map { it.dtoToEntity() }
        dao.insertAll(entities)
        val favorites = dao.getFavorites().associateBy { it.id }
        return response.results.map { it.dtoToDomain(favorites.containsKey(it.id)) }
    }

    override suspend fun getCachedMovies(): List<Movie> {
        return dao.getAllMovies().map { it.entityToDomain() }
    }

    override suspend fun getFavorites(): List<Movie> {
        return dao.getFavorites().map { it.entityToDomain() }
    }

    override suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        dao.updateFavorite(movieId, isFavorite)
    }

    override suspend fun cacheMovies(movies: List<Movie>) {
        dao.insertAll(movies.map { it.domainToEntity() })
    }
}
