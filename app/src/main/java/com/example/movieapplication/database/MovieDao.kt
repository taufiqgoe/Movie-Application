package com.example.movieapplication.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapplication.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Movie>

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM movie_table ORDER BY number")
    fun pagingSource(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}