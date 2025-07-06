package com.example.moviestest.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.local.entity.RemoteKeys
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
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }


        try {
            val response = api.discoverMoviesWithParams(DiscoverMovieParams(page = page))
            val movies = response.results
                .filter { !it.releaseDate.isNullOrBlank() }
                .map { it.dtoToEntity() }

            val endOfPaginationReached = response.results.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.movieDao().clearAll()
                }

                val keys = movies.map { movie ->
                    RemoteKeys(
                        movieId = movie.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (endOfPaginationReached) null else page + 1
                    )
                }
                database.remoteKeysDao().insertAll(keys)
                database.movieDao().insertAll(movies)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.lastItemOrNull()?.let { movie ->
            database.remoteKeysDao().remoteKeysMovieId(movie.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.firstItemOrNull()?.let { movie ->
            database.remoteKeysDao().remoteKeysMovieId(movie.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().remoteKeysMovieId(id)
            }
        }
    }
}