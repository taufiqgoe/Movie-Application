package com.example.movieapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.movieapplication.models.MovieAndPos
import com.example.movieapplication.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    lateinit var popular: LiveData<PagingData<MovieAndPos>>
    lateinit var topRated: LiveData<PagingData<MovieAndPos>>
    lateinit var upcoming: LiveData<PagingData<MovieAndPos>>

    @ExperimentalPagingApi
    fun getPopularFromDb() {
        viewModelScope.launch {
            popular = repository.getPopularFromDb()
        }
    }

    @ExperimentalPagingApi
    fun getTopRatedFromDb() {
        viewModelScope.launch {
            topRated = repository.getTopRatedFromDb()
        }
    }

    @ExperimentalPagingApi
    fun getUpcomingFromDb() {
        viewModelScope.launch {
            upcoming = repository.getUpcomingFromDb()
        }
    }
}