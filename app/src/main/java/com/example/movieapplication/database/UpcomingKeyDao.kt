package com.example.movieapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplication.models.key.UpcomingKey

@Dao
interface UpcomingKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: UpcomingKey)

    @Query("SELECT * FROM upcoming_keys ORDER BY upcomingId DESC LIMIT 1")
    suspend fun lastItemRemoteKey(): UpcomingKey?

    @Query("DELETE FROM upcoming_keys")
    suspend fun deleteAll()
}