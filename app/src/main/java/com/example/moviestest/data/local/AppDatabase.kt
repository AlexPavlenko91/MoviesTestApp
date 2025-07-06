package com.example.moviestest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.local.entity.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}