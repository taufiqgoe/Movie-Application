package com.example.movieapplication.models.key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_keys")
data class PopularKey(
    @PrimaryKey(autoGenerate = true)
    val popularId: Int,
    val movieId: Int,
    val page: Int?
)
