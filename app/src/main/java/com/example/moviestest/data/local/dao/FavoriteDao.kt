package com.example.moviestest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviestest.data.local.entity.FavoriteMovie
import com.example.moviestest.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

    @Query("SELECT movies.* FROM movies INNER JOIN favorites ON movies.id = favorites.movieId ORDER BY releaseDate DESC")
    fun getFavoritesFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fav: FavoriteMovie)

    @Query("DELETE FROM favorites WHERE movieId = :id")
    suspend fun delete(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE movieId = :id)")
    suspend fun exists(id: Int): Boolean
}