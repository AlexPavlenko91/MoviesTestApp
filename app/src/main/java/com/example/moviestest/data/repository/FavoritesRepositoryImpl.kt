package com.example.moviestest.data.repository

import com.example.moviestest.data.local.dao.FavoriteDao
import com.example.moviestest.data.local.entity.FavoriteMovie
import com.example.moviestest.data.mapper.entityToDomain
import com.example.moviestest.domain.model.Movie
import com.example.moviestest.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<Movie>> {
        return dao.getFavoritesFlow().map { list -> list.map { it.entityToDomain() } }
    }

    override suspend fun addToFavorites(movie: Movie) {
        dao.insert(FavoriteMovie(movieId = movie.id))
    }

    override suspend fun removeFromFavorites(movieId: Int) {
        dao.delete(movieId)
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return dao.exists(movieId)
    }
}