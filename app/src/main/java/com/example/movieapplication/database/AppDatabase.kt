package com.example.movieapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.MovieKey

@Database(entities = [Movie::class, MovieKey::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieKeyDao(): MovieKeyDao
}