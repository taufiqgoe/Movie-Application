package com.example.movieapplication.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val models: List<Movie>,
    val page: Int
)