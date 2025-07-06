package com.example.moviestest.di

import androidx.paging.Pager
import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.paging.MoviesPagerProvider
import com.example.moviestest.data.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @Provides
    fun provideMoviesPagerProvider(
        api: MovieApi,
        db: AppDatabase
    ): MoviesPagerProvider = MoviesPagerProvider(api, db)

    @Provides
    fun providePager(moviesPagerProvider: MoviesPagerProvider): Pager<Int, MovieEntity> {
        return moviesPagerProvider.providePager()
    }
}