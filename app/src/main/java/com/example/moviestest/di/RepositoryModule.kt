package com.example.moviestest.di

import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.data.repository.MovieRepositoryImpl
import com.example.moviestest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(
        api: MovieApi,
        dao: MovieDao
    ): MovieRepository = MovieRepositoryImpl(api, dao)
}