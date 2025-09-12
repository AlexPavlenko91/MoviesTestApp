package com.example.moviestest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "poster_url") val posterUrl: String?,

    @ColumnInfo(name = "release_date") val releaseDate: String,

    @ColumnInfo(name = "vote_average") val voteAverage: Double,

    @ColumnInfo(name = "vote_count") val voteCount: Int,

    @ColumnInfo(name = "overview") val overview: String,

    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false
)