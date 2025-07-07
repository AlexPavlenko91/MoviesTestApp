package com.example.moviestest.di

import android.content.Context
import androidx.room.Room
import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.dao.FavoriteDao
import com.example.moviestest.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    @Singleton
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()
}