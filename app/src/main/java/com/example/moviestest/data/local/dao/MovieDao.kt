package com.example.moviestest.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviestest.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY releaseDate DESC")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY releaseDate DESC")
    fun getFavoritesFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM movies ORDER BY releaseDate DESC")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAll()

}