package com.example.movieapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.key.PopularKey
import com.example.movieapplication.models.key.TopRatedKey
import com.example.movieapplication.models.key.UpcomingKey
import com.example.movieapplication.models.pos.PopularPos
import com.example.movieapplication.models.pos.TopRatedPos
import com.example.movieapplication.models.pos.UpcomingPos

@Database(
    entities = [
        Movie::class,
        PopularPos::class,
        TopRatedPos::class,
        UpcomingPos::class,
        PopularKey::class,
        TopRatedKey::class,
        UpcomingKey::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun popularPosDao(): PopularPosDao
    abstract fun topRatedPosDao(): TopRatedPosDao
    abstract fun upcomingPosDao(): UpcomingPosDao

    abstract fun popularKeyDao(): PopularKeyDao
    abstract fun topRatedKeyDao(): TopRatedKeyDao
    abstract fun upcomingKeyDao(): UpcomingKeyDao
}