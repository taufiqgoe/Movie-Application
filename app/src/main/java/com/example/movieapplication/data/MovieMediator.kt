package com.example.movieapplication.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapplication.api.Api
import com.example.movieapplication.database.AppDatabase
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.MovieKey
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieMediator(
    private val api: Api,
    private val database: AppDatabase
) : RemoteMediator<Int, Movie>() {
    private val movieDao = database.movieDao()
    private val movieKeyDao = database.movieKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        return try {
            val nextPage = when (loadType) {

                LoadType.REFRESH -> {
                    Log.d("MovieMediator", "REFRESH")
                    null
                }
                LoadType.PREPEND -> {
                    Log.d("MovieMediator", "PREPEND")
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                LoadType.APPEND -> {
                    Log.d("MovieMediator", "APPEND")
                    val remoteKey = database.withTransaction {
                        movieKeyDao.lastItemRemoteKey()
                    }
                    if (remoteKey.page == null) {
                        Log.d("MovieMediator", "APPEND > Remote key: null")
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.page.plus(1)
                }
            }

            val response = api.getPopularMovie(Api.API_KEY, nextPage ?: 1)
            Log.d("MovieMediator", "request: $nextPage")

            database.withTransaction {
                // REFRESH
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteAll()
                    movieKeyDao.deleteAll()
                }

                if (response.isSuccessful) {
                    Log.d("MovieMediator", "isSuccessful")
                    movieDao.insertAll(response.body()!!.models)
                    movieKeyDao.insertOrReplace(
                        MovieKey(0, response.body()!!.models.last().id, response.body()!!.page)
                    )
                } else {
                    Log.d("MovieMediator", "Request failed")
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = state.pages.size >= state.config.maxSize
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}