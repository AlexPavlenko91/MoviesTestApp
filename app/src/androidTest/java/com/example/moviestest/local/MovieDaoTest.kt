package com.example.moviestest.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.moviestest.data.local.AppDatabase
import com.example.moviestest.data.local.dao.MovieDao
import com.example.moviestest.mockEntity1
import com.example.moviestest.mockEntity2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class MovieDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.movieDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insert_and_getAllMovies() = runTest {
        val movies = listOf(mockEntity1, mockEntity2)
        dao.insertAll(movies)

        val result = dao.getAllMovies()
        assertEquals(2, result.size)
        assertEquals(movies[0].id, result[0].id)
    }

    @Test
    fun updateFavorite_shouldChangeIsFavorite() = runTest {
        val movie = mockEntity1.copy(isFavorite = false)
        dao.insertAll(listOf(movie))

        dao.updateFavorite(movie.id, true)
        val updated = dao.getFavoritesFlow().first().first()
        assertTrue(updated.isFavorite)
    }
}
