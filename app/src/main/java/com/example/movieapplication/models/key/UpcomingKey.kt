package com.example.movieapplication.models.key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_keys")
data class UpcomingKey(
    @PrimaryKey(autoGenerate = true)
    val upcomingId: Int,
    val movieId: Int,
    val page: Int?
)
