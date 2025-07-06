package com.example.moviestest.di

import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.paging.MoviesPagerProvider
import com.example.moviestest.data.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @Provides
    @Singleton
    fun provideMoviesPagerProvider(
        api: MovieApi,
        db: AppDatabase
    ): MoviesPagerProvider = MoviesPagerProvider(api, db)
}