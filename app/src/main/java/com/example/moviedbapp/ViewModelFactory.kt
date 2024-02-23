package com.example.moviedbapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedbapp.data.MovieRepository
import com.example.moviedbapp.di.Injection
import com.example.moviedbapp.ui.MovieViewModel


class ViewModelFactory(private val repository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(): ViewModelFactory {
            return synchronized(ViewModelFactory::class.java) {
                ViewModelFactory(Injection.provideRepository())
            }
        }
    }
}