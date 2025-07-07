package com.example.moviestest.di

import com.example.moviestest.data.local.dao.FavoriteDao
import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.repository.FavoritesRepositoryImpl
import com.example.moviestest.data.repository.MovieRepositoryImpl
import com.example.moviestest.domain.repository.FavoritesRepository
import com.example.moviestest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        dao: MovieDao
    ): MovieRepository = MovieRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        dao: FavoriteDao
    ): FavoritesRepository = FavoritesRepositoryImpl(dao)
}