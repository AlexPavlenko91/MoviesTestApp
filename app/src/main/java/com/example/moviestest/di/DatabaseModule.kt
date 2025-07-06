package com.example.moviestest.di

import android.content.Context
import androidx.room.Room
import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()
}