package com.example.moviedbapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviedbapp.data.MovieRepository
import com.example.moviedbapp.data.response.MovieDetailResponse
import com.example.moviedbapp.data.response.MovieGenreResponse
import com.example.moviedbapp.data.response.ResultsItem
import com.example.moviedbapp.data.response.ReviewResultsItem
import com.example.moviedbapp.data.response.VideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _listGenre = MutableLiveData<MovieGenreResponse>()
    val listGenre: LiveData<MovieGenreResponse> = _listGenre

    private val _movieDetail = MutableLiveData<MovieDetailResponse>()
    val movieDetail: LiveData<MovieDetailResponse> = _movieDetail

    private val _videoKey = MutableLiveData<String?>()
    val videoKey: LiveData<String?> = _videoKey


    fun getGenre() {
        _isLoading.value = true
        val client = repository.getGenres()
        client.enqueue(object : Callback<MovieGenreResponse> {
            override fun onResponse(
                call: Call<MovieGenreResponse>, response: Response<MovieGenreResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _isLoading.value = false
                        _listGenre.value = response.body()
                    }
                } else {
                    _isLoading.value = false
                    _errorMsg.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieGenreResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMsg.value = t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getMoviesByGenre(genreId: Int): LiveData<PagingData<ResultsItem>> {
        return repository.getMoviesByGenre(genreId).cachedIn(viewModelScope)
    }

    fun getMovieDetail(movieId: Int) {
        _isLoading.value = true
        val client = repository.getMovieDetail(movieId)
        client.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _isLoading.value = false
                        _movieDetail.value = response.body()
                    }
                } else {
                    _isLoading.value = false
                    _errorMsg.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMsg.value = t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getReview(movieId: Int): LiveData<PagingData<ReviewResultsItem>> {
        return repository.getReviews(movieId).cachedIn(viewModelScope)
    }

    fun getVideoKey(movieId: Int) {
        _isLoading.value = true
        val client = repository.getVideoKey(movieId)
        client.enqueue(object : Callback<VideoResponse> {
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _isLoading.value = false
                        _videoKey.value = response.body()!!.results.firstOrNull {
                            it?.name == "Official Trailer"
                        }?.key
                    }
                } else {
                    _isLoading.value = false
                    _errorMsg.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMsg.value = t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    companion object {
        private const val TAG = "MovieViewModel"
    }

}