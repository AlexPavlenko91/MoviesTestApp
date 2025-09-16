package com.example.moviestest.data.repository

import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.mockDomain1
import com.example.moviestest.data.paging.MoviesPagerProvider
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import kotlin.test.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private val dao = mockk<MovieDao>()
    private val pagerProvider = mockk<MoviesPagerProvider>(relaxed = true)
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(dao, pagerProvider)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `toggleFavorite should call dao with correct values`() = runTest {
        coEvery { dao.updateFavorite(1, true) } just Runs

        repository.toggleFavorite(1, true)

        coVerify(exactly = 1) { dao.updateFavorite(1, true) }
        confirmVerified(dao)
    }

    @Test
    fun `cacheMovies should insert mapped entities`() = runTest {
        val movie = mockDomain1
        coEvery { dao.insertAll(any()) } just Runs

        repository.cacheMovies(listOf(movie))

        coVerify {
            dao.insertAll(match { list ->
                list.size == 1 && list.first().id == movie.id
            })
        }
        confirmVerified(dao)
    }
}