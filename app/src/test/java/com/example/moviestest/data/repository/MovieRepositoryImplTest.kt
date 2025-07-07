package com.example.moviestest.data.repository

import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.mapper.dtoToDomain
import com.example.moviestest.data.mockDto1
import io.mockk.Awaits
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test


class MovieRepositoryImplTest {

    private val dao = mockk<MovieDao>()
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(dao)
    }

    @Test
    fun `toggleFavorite should call dao with correct values`() = runTest {
        coEvery { dao.updateFavorite(1, true) } just Awaits

        repository.toggleFavorite(1, true)

        coVerify { dao.updateFavorite(1, true) }
    }

    @Test
    fun `cacheMovies should insert mapped entities`() = runTest {
        val movie = mockDto1.dtoToDomain(isFavorite = false)
        coEvery { dao.insertAll(any()) } just Runs

        repository.cacheMovies(listOf(movie))

        coVerify { dao.insertAll(match { it.first().id == movie.id }) }
    }
}
