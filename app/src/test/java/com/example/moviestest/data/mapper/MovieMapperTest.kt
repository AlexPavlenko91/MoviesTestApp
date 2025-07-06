package com.example.moviestest.data.mapper

import com.example.moviestest.data.mockDomain
import com.example.moviestest.data.mockDto1
import com.example.moviestest.data.mockEntity1
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import kotlin.test.Test

class MovieMapperTest {


    @Test
    fun `map MovieDto to MovieEntity correctly`() {
        val entity = mockDto1.dtoToEntity()

        assertEquals(mockDto1.id, entity.id)
        assertEquals(mockDto1.title, entity.title)
        assertEquals("$TMDB_IMAGE_BASE_URL${mockDto1.posterPath}", entity.posterUrl)
        assertEquals(mockDto1.releaseDate, entity.releaseDate)
        assertEquals(mockDto1.voteAverage, entity.voteAverage, 0.0)
        assertEquals(mockDto1.voteCount, entity.voteCount)
        assertEquals(mockDto1.overview, entity.overview)
        assertFalse(entity.isFavorite)
    }

    @Test
    fun `map MovieEntity to Movie domain model correctly`() {
        val entity = mockDto1.dtoToEntity()
        val domain = entity.entityToDomain()

        assertEquals(entity.id, domain.id)
        assertEquals(entity.title, domain.title)
        assertEquals(entity.posterUrl, domain.posterUrl)
        assertEquals(entity.releaseDate, domain.releaseDate)
        assertEquals(entity.voteAverage, domain.voteAverage, 0.0)
        assertEquals(entity.voteCount, domain.voteCount)
        assertEquals(entity.overview, domain.overview)
        assertEquals(entity.isFavorite, domain.isFavorite)
    }

    @Test
    fun `map MovieDto to Movie domain model correctly`() {
        val domain = mockDto1.dtoToDomain(isFavorite = true)

        assertEquals(mockDto1.id, domain.id)
        assertEquals(mockDto1.title, domain.title)
        assertEquals("$TMDB_IMAGE_BASE_URL${mockDto1.posterPath}", domain.posterUrl)
        assertEquals(mockDto1.releaseDate, domain.releaseDate)
        assertEquals(mockDto1.voteAverage, domain.voteAverage, 0.0)
        assertEquals(mockDto1.voteCount, domain.voteCount)
        assertEquals(mockDto1.overview, domain.overview)
        assertTrue(domain.isFavorite)
    }

    @Test
    fun `map Movie domain model to MovieEntity correctly`() {

        val entity = mockDomain.domainToEntity()

        assertEquals(mockDomain.id, entity.id)
        assertEquals(mockDomain.title, entity.title)
        assertEquals(mockDomain.posterUrl, entity.posterUrl)
        assertEquals(mockDomain.releaseDate, entity.releaseDate)
        assertEquals(mockDomain.voteAverage, entity.voteAverage, 0.0)
        assertEquals(mockDomain.voteCount, entity.voteCount)
        assertEquals(mockDomain.overview, entity.overview)
        assertEquals(mockDomain.isFavorite, entity.isFavorite)
    }

    @Test
    fun `map MovieEntity to Movie domain model correctly with isFavorite`() {

        println("Entity: $mockEntity1")

        val domain = mockEntity1.entityToDomain()

        assertEquals(mockEntity1.id, domain.id)
        assertEquals(mockEntity1.title, domain.title)
        assertEquals(mockEntity1.posterUrl, domain.posterUrl)
        assertEquals(mockEntity1.releaseDate, domain.releaseDate)
        assertEquals(mockEntity1.voteAverage, domain.voteAverage, 0.0)
        assertEquals(mockEntity1.voteCount, domain.voteCount)
        assertEquals(mockEntity1.overview, domain.overview)
        assertEquals(mockEntity1.isFavorite, domain.isFavorite)
    }
}