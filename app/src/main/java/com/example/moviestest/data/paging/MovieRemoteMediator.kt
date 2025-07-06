package com.example.moviestest.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.mapper.dtoToEntity
import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.data.remote.dto.DiscoverMovieParams
import com.example.moviestest.data.remote.extension.discoverMoviesWithParams


@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: MovieApi,
    private val database: AppDatabase
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                (state.pages.size + 1)
            }
        }

        return try {
            val response = api.discoverMoviesWithParams(DiscoverMovieParams(page = page))
            val movies = response.results.map { it.dtoToEntity() }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieDao().clearAll()
                }
                database.movieDao().insertAll(movies)
            }

            MediatorResult.Success(endOfPaginationReached = response.results.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}