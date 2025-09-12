package com.example.moviestest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class FavoriteMovie(
    @PrimaryKey
    @ColumnInfo(name = "movie_id") val movieId: Int
)