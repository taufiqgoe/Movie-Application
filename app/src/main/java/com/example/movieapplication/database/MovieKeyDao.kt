package com.example.movieapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplication.models.MovieKey

@Dao
interface MovieKeyDao {
    @Insert
    suspend fun insertOrReplace(remoteKey: MovieKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<MovieKey>)

    @Query("SELECT * FROM remote_keys WHERE movieId = :query")
    suspend fun remoteKeyByQuery(query: Int): MovieKey

    @Query("SELECT * FROM remote_keys ORDER BY number DESC LIMIT 1")
    suspend fun lastItemRemoteKey(): MovieKey

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()
}