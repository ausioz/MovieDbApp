package com.example.moviedbapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviedbapp.data.remote.ApiService
import com.example.moviedbapp.data.response.MovieDetailResponse
import com.example.moviedbapp.data.response.MovieGenreResponse
import com.example.moviedbapp.data.response.VideoResponse
import com.example.moviedbapp.data.paging.movie.MovieByGenrePagingSource
import com.example.moviedbapp.data.paging.review.UserReviewPagingSource
import com.example.moviedbapp.data.response.ResultsItem
import com.example.moviedbapp.data.response.ReviewResultsItem
import retrofit2.Call

class MovieRepository private constructor(
    private val apiService: ApiService
) {

    fun getGenres(): Call<MovieGenreResponse> {
        return apiService.getGenres()
    }

    fun getMoviesByGenre(genreId: Int): LiveData<PagingData<ResultsItem>> {
        return Pager(config = PagingConfig(
            pageSize = 1
        ), pagingSourceFactory = {
            MovieByGenrePagingSource(apiService, genreId)
        }).liveData
    }

    fun getMovieDetail(movieId: Int): Call<MovieDetailResponse> {
        return apiService.getMovieDetail(movieId)
    }

    fun getVideoKey(movieId: Int): Call<VideoResponse> {
        return apiService.getMovieVideos(movieId)
    }

    fun getReviews(movieId: Int): LiveData<PagingData<ReviewResultsItem>> {
        return Pager(config = PagingConfig(
            pageSize = 3
        ), pagingSourceFactory = {
            UserReviewPagingSource(apiService, movieId)
        }).liveData
    }

    companion object {
        fun getInstance(
            apiService: ApiService
        ): MovieRepository = synchronized(this) {
            MovieRepository(apiService)
        }
    }
}