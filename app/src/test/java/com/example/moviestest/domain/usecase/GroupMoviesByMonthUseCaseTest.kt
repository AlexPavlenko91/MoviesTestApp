package com.example.moviestest.domain.usecase

import com.example.moviestest.data.mockDomain1
import com.example.moviestest.data.mockDomain2
import com.example.moviestest.data.mockDomain2023
import com.example.moviestest.data.mockDomain2024
import com.example.moviestest.domain.model.YearMonthKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import kotlin.test.Test


@ExperimentalCoroutinesApi
class GroupMoviesByMonthUseCaseTest {

    private val useCase = GroupMoviesByMonthUseCase()

    @Test
    fun `groups movies correctly by year and month`() {
        val movies = listOf(mockDomain1, mockDomain2)

        val grouped = useCase(movies)

        assertEquals(2, grouped.size)
        assertTrue(grouped.containsKey(YearMonthKey(2025, 2)))
        assertEquals(1, grouped[YearMonthKey(2025, 1)]?.size)
    }

    @Test
    fun `skips movies with empty or invalid release date`() {
        val movies =
            listOf(mockDomain1.copy(releaseDate = "invalid"), mockDomain2.copy(releaseDate = ""))

        val grouped = useCase(movies)
        assertTrue(grouped.isEmpty())
    }

    @Test
    fun `sorts months in descending order`() {
        val movies = listOf(mockDomain2023, mockDomain2024)

        val grouped = useCase(movies)
        val keys = grouped.keys.toList()
        assertEquals(YearMonthKey(2024, 1), keys[0])
        assertEquals(YearMonthKey(2023, 5), keys[1])
    }
}