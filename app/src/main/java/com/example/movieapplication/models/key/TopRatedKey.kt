package com.example.movieapplication.models.key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Top_rated_keys")
data class TopRatedKey(
    @PrimaryKey(autoGenerate = true)
    val topRatedId: Int,
    val movieId: Int,
    val page: Int?
)
