package com.example.movieapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.pos.PopularPos

@Dao
interface PopularPosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PopularPos>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(popularPos: PopularPos)

    @Query("DELETE FROM popular_pos")
    suspend fun deleteAll()
}