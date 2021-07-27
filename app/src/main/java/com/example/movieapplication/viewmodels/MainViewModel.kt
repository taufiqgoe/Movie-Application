package com.example.movieapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.MovieResponse
import com.example.movieapplication.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val myResponse: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    lateinit var data: LiveData<PagingData<Movie>>

    fun getPopularMovie(page: Int) {
        viewModelScope.launch {
            val response = repository.getPopularMovie(page)
            myResponse.value = response
        }
    }

    fun getPopularWithPage() {
        viewModelScope.launch {
            data = repository.getPopularWithPage()
        }
    }

    @ExperimentalPagingApi
    fun getPopularWithDb() {
        viewModelScope.launch {
            data = repository.getPopularWithDb()
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            val response = repository.searchMovie(query)
            myResponse.value = response
        }
    }
}