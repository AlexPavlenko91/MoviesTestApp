package com.example.moviestest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.local.dao.RemoteKeysDao
import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.local.entity.RemoteKeys


@Database(
    entities = [MovieEntity::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}