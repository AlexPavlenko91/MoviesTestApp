package com.example.moviestest.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.mapper.domainToEntity
import com.example.moviestest.data.mapper.entityToDomain
import com.example.moviestest.data.paging.MoviesPagerProvider
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
    private val pagerProvider: MoviesPagerProvider
) : MovieRepository {

    override fun pagedMovies(): Flow<PagingData<Movie>> =
        pagerProvider.providePager()
            .flow
            .map { paging -> paging.map { it.entityToDomain() } }

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
