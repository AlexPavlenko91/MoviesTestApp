package com.example.moviestest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviestest.data.local.dao.FavoriteDao
import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.local.dao.RemoteKeysDao
import com.example.moviestest.data.local.entity.FavoriteMovie
import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.local.entity.RemoteKeys


@Database(
    entities = [MovieEntity::class, RemoteKeys::class, FavoriteMovie::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun favoriteDao(): FavoriteDao
}