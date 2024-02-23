package com.example.moviedbapp.ui.movielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.ViewModelFactory
import com.example.moviedbapp.data.paging.LoadingStateAdapter
import com.example.moviedbapp.data.paging.movie.MovieByGenreListAdapter
import com.example.moviedbapp.databinding.ActivityMovieByGenreBinding
import com.example.moviedbapp.ui.MovieViewModel
import com.example.moviedbapp.ui.cutomview.LoadingDialogFragment
import com.example.moviedbapp.ui.main.MainPagerAdapter

class MovieByGenreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieByGenreBinding

    private val viewModel by viewModels<MovieViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var movieByGenreListAdapter: MovieByGenreListAdapter

    private val loadingDialog = LoadingDialogFragment()
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
        binding.recyclerView.adapter = movieByGenreListAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                movieByGenreListAdapter.retry()
            })
        viewModel.getMoviesByGenre(extraGenreId).observe(this) {
            movieByGenreListAdapter.submitData(lifecycle, it)
        }
    }

    private fun showError(errorMsg: String?) {
        Toast.makeText(this, "Error! \n$errorMsg", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        loadingDialog.isCancelable = false
        if (isLoading) {
            loadingDialog.show(supportFragmentManager, "loadingDialog")
        } else {
            if (loadingDialog.isVisible) loadingDialog.dismiss()
        }
    }
}