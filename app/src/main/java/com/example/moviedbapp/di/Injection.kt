package com.example.moviedbapp.di

import com.example.moviedbapp.BuildConfig
import com.example.moviedbapp.data.MovieRepository
import com.example.moviedbapp.data.remote.ApiConfig

object Injection {
    fun provideRepository(): MovieRepository {
        val apiService = ApiConfig.getApiService(BuildConfig.TMDB_API_KEY)
        return MovieRepository.getInstance(apiService)
    }
}