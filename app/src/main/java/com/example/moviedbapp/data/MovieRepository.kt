package com.example.moviedbapp.data

import com.example.moviedbapp.data.remote.ApiService
import com.example.moviedbapp.data.response.DiscoverMovieResponse
import com.example.moviedbapp.data.response.MovieDetailResponse
import com.example.moviedbapp.data.response.MovieGenreResponse
import com.example.moviedbapp.data.response.UserReviewResponse
import com.example.moviedbapp.data.response.VideoResponse
import retrofit2.Call

class MovieRepository private constructor(
    private val apiService: ApiService
) {

    fun getGenres(): Call<MovieGenreResponse> {
        return apiService.getGenres()
    }

    fun getMoviesByGenre(genreId: Int): Call<DiscoverMovieResponse> {
        return apiService.getMoviesByGenre(genreId)
    }

    fun getMovieDetail(movieId: Int): Call<MovieDetailResponse> {
        return apiService.getMovieDetail(movieId)
    }

    fun getVideoKey(movieId:Int):Call<VideoResponse>{
        return apiService.getMovieVideos(movieId)
    }

    fun getReviews(movieId: Int):Call<UserReviewResponse>{
        return apiService.getReviews(movieId)
    }

    companion object {
        fun getInstance(
            apiService: ApiService
        ): MovieRepository = synchronized(this) {
            MovieRepository(apiService)
        }
    }
}