package com.example.movieapplication.models.pos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_rated_pos")
data class TopRatedPos(
    @PrimaryKey(autoGenerate = true)
    val topRatedId: Int,
    val movieId: Int,
    val position: Int
)
