package com.example.moviestest.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviestest.data.local.entity.MovieEntity
import com.example.moviestest.data.mapper.entityToDomain
import com.example.moviestest.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetPagedMoviesUseCase @Inject constructor(
    private val pager: Pager<Int, MovieEntity>
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return pager.flow.map { it.map { entity -> entity.entityToDomain() } }
    }
}