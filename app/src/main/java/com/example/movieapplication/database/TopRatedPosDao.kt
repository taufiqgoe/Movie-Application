package com.example.movieapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.pos.TopRatedPos

@Dao
interface TopRatedPosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<TopRatedPos>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topRatedPos: TopRatedPos)

    @Query("DELETE FROM top_rated_pos")
    suspend fun deleteAll()
}