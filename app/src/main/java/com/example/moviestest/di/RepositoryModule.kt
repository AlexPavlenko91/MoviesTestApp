package com.example.moviestest.di

import com.example.moviestest.data.repository.FavoritesRepositoryImpl
import com.example.moviestest.data.repository.MovieRepositoryImpl
import com.example.moviestest.domain.repository.FavoritesRepository
import com.example.moviestest.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository
}