package com.example.moviestest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class FavoriteMovie(@PrimaryKey val movieId: Int)