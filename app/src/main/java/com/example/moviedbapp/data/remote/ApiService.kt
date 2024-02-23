package com.example.moviedbapp.data.remote

import com.example.moviedbapp.data.response.DiscoverMovieResponse
import com.example.moviedbapp.data.response.MovieDetailResponse
import com.example.moviedbapp.data.response.MovieGenreResponse
import com.example.moviedbapp.data.response.UserReviewResponse
import com.example.moviedbapp.data.response.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    fun getGenres(): Call<MovieGenreResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") id: Int,
        @Query("page") page: Int,
    ): DiscoverMovieResponse

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") id: Int,
    ): Call<MovieDetailResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") id: Int,
    ): Call<VideoResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") id: Int,
        @Query("page") page: Int,
    ): UserReviewResponse


}
