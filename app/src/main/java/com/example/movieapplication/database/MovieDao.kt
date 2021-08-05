package com.example.movieapplication.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.MovieAndPos

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Movie>

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM movie_table JOIN popular_pos ON id = movieId ORDER BY position")
    fun popularSource(): PagingSource<Int, MovieAndPos>

    @Transaction
    @Query("SELECT * FROM movie_table JOIN top_rated_pos ON id = movieId ORDER BY position")
    fun topRatedSource(): PagingSource<Int, MovieAndPos>

    @Transaction
    @Query("SELECT * FROM movie_table JOIN upcoming_pos ON id = movieId ORDER BY position")
    fun upcomingSource(): PagingSource<Int, MovieAndPos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}