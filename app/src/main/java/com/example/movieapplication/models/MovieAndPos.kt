package com.example.movieapplication.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.movieapplication.models.pos.PopularPos
import com.example.movieapplication.models.pos.TopRatedPos
import com.example.movieapplication.models.pos.UpcomingPos

data class MovieAndPos(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    ) val popularPos: PopularPos?,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    ) val topRatedPos: TopRatedPos?,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    ) val upcomingPos: UpcomingPos?
)
