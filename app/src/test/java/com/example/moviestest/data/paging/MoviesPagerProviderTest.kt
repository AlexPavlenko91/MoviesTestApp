package com.example.moviestest.data.paging

import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.data.remote.api.MovieApi
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Before
import kotlin.test.Test


class MoviesPagerProviderTest {

    private lateinit var pagerProvider: MoviesPagerProvider

    private val api = mockk<MovieApi>(relaxed = true)
    private val db = mockk<AppDatabase>(relaxed = true)
    private val dao = mockk<MovieDao>(relaxed = true)

    @Before
    fun setup() {
        every { db.movieDao() } returns dao
        pagerProvider = MoviesPagerProvider(api, db)
    }

    @Test
    fun `providePager returns non-null`() {
        val pager = pagerProvider.providePager()
        assertNotNull(pager)
    }

    @Test
    fun `providePager returns non-null flow`() {
        val pager = pagerProvider.providePager()
        assertNotNull(pager.flow)
    }
}
