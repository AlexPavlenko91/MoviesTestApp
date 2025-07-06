package com.example.moviestest.data.remote.dto

import kotlinx.serialization.SerialName

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)