package com.example.movieapplication.models.pos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_pos")
data class PopularPos(
    @PrimaryKey(autoGenerate = true)
    val popularId: Int,
    val movieId: Int,
    val position: Int
)
