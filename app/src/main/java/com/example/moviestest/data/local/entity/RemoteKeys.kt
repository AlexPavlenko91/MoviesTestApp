package com.example.moviestest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val movieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)