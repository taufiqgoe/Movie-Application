package com.example.movieapplication.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapplication.api.Api
import com.example.movieapplication.database.AppDatabase
import com.example.movieapplication.models.MovieAndPos
import com.example.movieapplication.models.key.PopularKey
import com.example.movieapplication.models.MovieResponse
import com.example.movieapplication.models.key.TopRatedKey
import com.example.movieapplication.models.key.UpcomingKey
import com.example.movieapplication.models.pos.PopularPos
import com.example.movieapplication.models.pos.TopRatedPos
import com.example.movieapplication.models.pos.UpcomingPos
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieMediator(
    private val api: Api,
    private val database: AppDatabase,
    private val requestType: RequestType
) : RemoteMediator<Int, MovieAndPos>() {
    private val movieDao = database.movieDao()

    private val popularPosDao = database.popularPosDao()
    private val topRatedPosDao = database.topRatedPosDao()
    private val upcomingPosDao = database.upcomingPosDao()

    private val popularKeyDao = database.popularKeyDao()
    private val topRatedKeyDao = database.topRatedKeyDao()
    private val upcomingKeyDao = database.upcomingKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieAndPos>
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
                    val remoteKey = getRemoteKey(requestType)
                    if (remoteKey == null) {
                        Log.d("MovieMediator", "APPEND > Remote key: null")
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.plus(1)
                }
            }

            val response = request(requestType, nextPage)
            Log.d("MovieMediator", "request: $nextPage")

            database.withTransaction {

                // REFRESH
                if (loadType == LoadType.REFRESH) {
                    clearDatabase(requestType)
                }

                if (response.isSuccessful) {

                    val size = response.body()!!.models.size
                    val page = response.body()!!.page
                    val list = response.body()!!.models

                    when (requestType) {
                        RequestType.Popular -> {
                            list.forEachIndexed { index, movie ->
                                popularPosDao.insert(
                                    PopularPos(0, movie.id, getPosition(size, page, index.plus(1)))
                                )
                            }
                            popularKeyDao.insertOrReplace(
                                PopularKey(0, list.last().id, page)
                            )
                        }

                        RequestType.TopRated -> {
                            list.forEachIndexed { index, movie ->
                                topRatedPosDao.insert(
                                    TopRatedPos(0, movie.id, getPosition(size, page, index.plus(1)))
                                )
                            }
                            topRatedKeyDao.insertOrReplace(
                                TopRatedKey(0, list.last().id, page)
                            )
                        }

                        RequestType.Upcoming -> {
                            list.forEachIndexed { index, movie ->
                                upcomingPosDao.insert(
                                    UpcomingPos(0, movie.id, getPosition(size, page, index.plus(1)))
                                )
                            }
                            upcomingKeyDao.insertOrReplace(
                                UpcomingKey(0, list.last().id, page)
                            )
                        }
                    }

                    Log.d("MovieMediator", "isSuccessful")
                    movieDao.insertAll(list)

                } else {
                    Log.d("MovieMediator", "Request failed")
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = false
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    enum class RequestType {
        Popular, TopRated, Upcoming
    }

    private suspend fun request(requestType: RequestType, nextPage: Int?): Response<MovieResponse> {
        return when (requestType) {
            RequestType.Popular -> api.getPopularMovie(Api.API_KEY, nextPage ?: 1)
            RequestType.TopRated -> api.getTopRatedMovie(Api.API_KEY, nextPage ?: 1)
            RequestType.Upcoming -> api.getUpcomingMovie(Api.API_KEY, nextPage ?: 1)
        }
    }

    private fun getPosition(itemSize: Int, page: Int, currentPosition: Int): Int {
        return ((itemSize * page) - itemSize) + currentPosition
    }

    private suspend fun getRemoteKey(requestType: RequestType): Int? {
        return when (requestType) {
            RequestType.Popular -> {
                popularKeyDao.lastItemRemoteKey()?.page
            }
            RequestType.TopRated -> {
                topRatedKeyDao.lastItemRemoteKey()?.page
            }
            RequestType.Upcoming -> {
                upcomingKeyDao.lastItemRemoteKey()?.page
            }
        }
    }

    private suspend fun clearDatabase(requestType: RequestType) {
        when (requestType) {
            RequestType.Popular -> {
                popularKeyDao.deleteAll()
                popularPosDao.deleteAll()
            }
            RequestType.TopRated -> {
                topRatedPosDao.deleteAll()
                topRatedKeyDao.deleteAll()
            }
            RequestType.Upcoming -> {
                upcomingPosDao.deleteAll()
                upcomingKeyDao.deleteAll()
            }
        }
//        movieDao.deleteAll()
    }
}