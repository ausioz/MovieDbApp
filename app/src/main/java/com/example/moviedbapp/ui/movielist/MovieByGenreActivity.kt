package com.example.moviedbapp.ui.movielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.ViewModelFactory
import com.example.moviedbapp.data.paging.LoadingStateAdapter
import com.example.moviedbapp.data.paging.movie.MovieByGenreListAdapter
import com.example.moviedbapp.databinding.ActivityMovieByGenreBinding
import com.example.moviedbapp.ui.MovieViewModel
import com.example.moviedbapp.ui.main.MainPagerAdapter

class MovieByGenreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieByGenreBinding

    private val viewModel by viewModels<MovieViewModel> {
        ViewModelFactory.getInstance()
    }

    private lateinit var movieByGenreListAdapter: MovieByGenreListAdapter

    private var extraGenreId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieByGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extraGenreId = intent.getIntExtra(MainPagerAdapter.EXTRA_GENRE, 0)

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        getData()
    }

    private fun getData() {
        movieByGenreListAdapter = MovieByGenreListAdapter()
        binding.recyclerView.adapter =
            movieByGenreListAdapter.withLoadStateFooter(footer = LoadingStateAdapter {
                movieByGenreListAdapter.retry()
            })
        viewModel.getMoviesByGenre(extraGenreId).observe(this) {
            movieByGenreListAdapter.submitData(lifecycle, it)
        }
    }
}