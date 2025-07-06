package com.example.moviestest.data.repository

import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.mapper.domainToEntity
import com.example.moviestest.data.mapper.dtoToDomain
import com.example.moviestest.data.mapper.dtoToEntity
import com.example.moviestest.data.mapper.entityToDomain
import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.data.remote.dto.DiscoverMovieParams
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun getRemoteMovies(page: Int): List<Movie> {
        val params = DiscoverMovieParams(page = page)

        val response = api.discoverMovies(
            page = params.page,
            sortBy = params.sortBy,
            voteAverageMin = params.voteAverageMin,
            voteCountMin = params.voteCountMin,
            includeAdult = params.includeAdult,
            includeVideo = params.includeVideo,
            language = params.language
        )

        val entities = response.results.map { it.dtoToEntity() }
        dao.insertAll(entities)

        val favorites = dao.getFavoritesFlow()
            .first()
            .associateBy { it.id }

        return response.results.map { it.dtoToDomain(isFavorite = favorites.containsKey(it.id)) }
    }

    override suspend fun getCachedMovies(): List<Movie> {
        return dao.getAllMovies().map { it.entityToDomain() }
    }

    override suspend fun getFavorites(): Flow<List<Movie>> {
        return dao.getFavoritesFlow().map { list -> list.map { it.entityToDomain() } }
    }

    override suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        dao.updateFavorite(movieId, isFavorite)
    }

    override suspend fun cacheMovies(movies: List<Movie>) {
        dao.insertAll(movies.map { it.domainToEntity() })
    }
}
