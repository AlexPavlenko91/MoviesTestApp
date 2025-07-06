package com.example.moviestest.remote

import com.example.moviestest.BuildConfig
import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.data.remote.dto.DiscoverMovieParams
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.moviestest.data.remote.extension.discoverMovies


@ExperimentalCoroutinesApi
class MovieApiTest {

    private lateinit var api: MovieApi

    @Before
    fun setup() {
        val authInterceptor = com.example.moviestest.di.NetworkModule.provideAuthInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(MovieApi::class.java)
    }

    @Test
    fun testDiscoverMoviesReturnsResults() = runTest {
        val response = api.discoverMovies(DiscoverMovieParams())
        assertTrue(response.results.isNotEmpty())
        println("First movie title: ${response.results.firstOrNull()?.title}")
    }
}