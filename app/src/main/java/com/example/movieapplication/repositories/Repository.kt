package com.example.movieapplication.repositories

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.movieapplication.api.Api
import com.example.movieapplication.data.MovieMediator
import com.example.movieapplication.database.AppDatabase
import com.example.movieapplication.database.MovieDao
import com.example.movieapplication.models.MovieAndPos
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val movieDao: MovieDao,
    private val database: AppDatabase
) {

    @ExperimentalPagingApi
    fun getPopularFromDb(): LiveData<PagingData<MovieAndPos>> {
        return Pager(
            config = PagingConfig(20, maxSize = 100, enablePlaceholders = true),
            remoteMediator = MovieMediator(api, database, MovieMediator.RequestType.Popular)
        ) {
            movieDao.popularSource()
        }.liveData
    }

    @ExperimentalPagingApi
    fun getTopRatedFromDb(): LiveData<PagingData<MovieAndPos>> {
        return Pager(
            config = PagingConfig(20, maxSize = 100, enablePlaceholders = true),
            remoteMediator = MovieMediator(api, database, MovieMediator.RequestType.TopRated)
        ) {
            movieDao.topRatedSource()
        }.liveData
    }

    @ExperimentalPagingApi
    fun getUpcomingFromDb(): LiveData<PagingData<MovieAndPos>> {
        return Pager(
            config = PagingConfig(20, maxSize = 100, enablePlaceholders = true),
            remoteMediator = MovieMediator(api, database, MovieMediator.RequestType.Upcoming)
        ) {
            movieDao.upcomingSource()
        }.liveData
    }
}