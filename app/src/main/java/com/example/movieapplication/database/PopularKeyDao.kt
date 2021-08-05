package com.example.movieapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplication.models.key.PopularKey

@Dao
interface PopularKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: PopularKey)

    @Query("SELECT * FROM popular_keys ORDER BY popularId DESC LIMIT 1")
    suspend fun lastItemRemoteKey(): PopularKey?

    @Query("DELETE FROM popular_keys")
    suspend fun deleteAll()
}