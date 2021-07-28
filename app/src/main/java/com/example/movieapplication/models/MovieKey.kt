package com.example.movieapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class MovieKey(
    @PrimaryKey(autoGenerate = true)
    val number: Int,
    val movieId: Int,
    val page: Int?
)
