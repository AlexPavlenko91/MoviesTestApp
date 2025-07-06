package com.example.moviestest.data.remote.extension

import com.example.moviestest.data.remote.api.MovieApi
import com.example.moviestest.data.remote.dto.DiscoverMovieParams
import com.example.moviestest.data.remote.dto.MovieResponseDto

suspend fun MovieApi.discoverMoviesWithParams(params: DiscoverMovieParams): MovieResponseDto {
    return discoverMovies(
        page = params.page,
        sortBy = params.sortBy,
        voteAverageMin = params.voteAverageMin,
        voteCountMin = params.voteCountMin,
        includeAdult = params.includeAdult,
        includeVideo = params.includeVideo,
        language = params.language
    )
}