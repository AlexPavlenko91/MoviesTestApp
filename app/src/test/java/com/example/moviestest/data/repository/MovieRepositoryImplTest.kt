package com.example.moviestest.data.repository

import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.mapper.dtoToDomain
import com.example.moviestest.data.mapper.dtoToEntity
import com.example.moviestest.data.mapper.entityToDomain
import com.example.moviestest.data.mockDto1
import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.data.remote.dto.MovieResponseDto
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test


class MovieRepositoryImplTest {

    private val api = mockk<MovieApi>()
    private val dao = mockk<MovieDao>()
    private lateinit var repository: MovieRepositoryImpl


    private val movieEntity = mockDto1.dtoToEntity()
    private val domainMovie = mockDto1.dtoToDomain(isFavorite = true)

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(api, dao)
    }

    @Test
    fun `getRemoteMovies should fetch, cache, and map correctly`() = runTest {
        // Arrange
        coEvery {
            api.discoverMovies(
                page = 1,
                sortBy = any(),
                voteAverageMin = any(),
                voteCountMin = any(),
                includeAdult = any(),
                includeVideo = any(),
                language = any()
            )
        } returns MovieResponseDto(
            page = 1,
            results = listOf(mockDto1),
            totalPages = 1,
            totalResults = 1
        )

        coEvery { dao.insertAll(any()) } just Runs
        coEvery { dao.getFavoritesFlow() } returns flowOf(listOf(movieEntity.copy(isFavorite = true)))

        // Act
        val result = repository.getRemoteMovies(page = 1)

        // Assert
        assertEquals(1, result.size)
        assertEquals(domainMovie.copy(isFavorite = true), result.first())
        coVerify { dao.insertAll(match { it.first().id == movieEntity.id }) }
    }

    @Test
    fun `getCachedMovies should return mapped entities`() = runTest {
        coEvery { dao.getAllMovies() } returns listOf(movieEntity)

        val result = repository.getCachedMovies()

        assertEquals(1, result.size)
        assertEquals(movieEntity.entityToDomain(), result.first())
    }

    @Test
    fun `getFavorites should return flow of domain movies`() = runTest {
        coEvery { dao.getFavoritesFlow() } returns flowOf(listOf(movieEntity))

        val result = repository.getFavorites().first()

        assertEquals(1, result.size)
        assertEquals(movieEntity.entityToDomain(), result.first())
    }

    @Test
    fun `toggleFavorite should call dao with correct values`() = runTest {
        coEvery { dao.updateFavorite(1, true) } just Runs

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
