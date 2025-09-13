package com.example.moviestest.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.remote.api.MovieApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesPagerProvider @Inject constructor(
    private val api: MovieApi,
    private val db: AppDatabase
) {
    companion object {
        private const val PAGE_SIZE = 20
    }

    @OptIn(ExperimentalPagingApi::class)
    fun providePager(): Pager<Int, MovieEntity> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MovieRemoteMediator(api, db),
            pagingSourceFactory = { db.movieDao().pagingSource() }
        )
}