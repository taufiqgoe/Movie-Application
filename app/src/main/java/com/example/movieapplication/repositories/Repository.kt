package com.example.movieapplication.repositories

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.movieapplication.api.Api
import com.example.movieapplication.api.Api.Companion.API_KEY
import com.example.movieapplication.data.MovieMediator
import com.example.movieapplication.data.MoviePagingSource
import com.example.movieapplication.database.AppDatabase
import com.example.movieapplication.database.MovieDao
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api,
    private val movieDao: MovieDao,
    private val database: AppDatabase
) {
    suspend fun getPopularMovie(page: Int): Response<MovieResponse> {
        return api.getPopularMovie(API_KEY, page)
    }

    fun getPopularWithPage() = Pager(
        config = PagingConfig(
            pageSize = 10, maxSize = 100
        ),
        pagingSourceFactory = { MoviePagingSource(api) }
    ).liveData

    @ExperimentalPagingApi
    fun getPopularWithDb(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(20, maxSize = 100, enablePlaceholders = true),
            remoteMediator = MovieMediator(api, database)
        ) {
            movieDao.pagingSource()
        }.liveData
    }


    suspend fun searchMovie(query: String): Response<MovieResponse> {
        return api.searchMovie(API_KEY, query)
    }
}