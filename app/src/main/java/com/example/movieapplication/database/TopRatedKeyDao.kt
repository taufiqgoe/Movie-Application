package com.example.movieapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplication.models.key.TopRatedKey

@Dao
interface TopRatedKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: TopRatedKey)

    @Query("SELECT * FROM top_rated_keys ORDER BY topRatedId DESC LIMIT 1")
    suspend fun lastItemRemoteKey(): TopRatedKey?

    @Query("DELETE FROM top_rated_keys")
    suspend fun deleteAll()
}