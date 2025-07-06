package com.example.moviestest.data.remote.api

import com.example.moviestest.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("vote_average.gte") voteAverageMin: Double,
        @Query("vote_count.gte") voteCountMin: Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("language") language: String
    ): MovieResponseDto
}