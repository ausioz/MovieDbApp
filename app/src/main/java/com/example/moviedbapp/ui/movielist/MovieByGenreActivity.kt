package com.example.moviedbapp.ui.movielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.ViewModelFactory
import com.example.moviedbapp.data.response.ResultsItem
import com.example.moviedbapp.databinding.ActivityMovieByGenreBinding
import com.example.moviedbapp.ui.MovieViewModel
import com.example.moviedbapp.ui.cutomview.LoadingDialogFragment
import com.example.moviedbapp.ui.main.MainPagerAdapter

class MovieByGenreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieByGenreBinding

    private val viewModel by viewModels<MovieViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var movieByGenrePagerAdapter: MovieByGenrePagerAdapter

    private val loadingDialog = LoadingDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieByGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraGenreId = intent.getIntExtra(MainPagerAdapter.EXTRA_GENRE,0)

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        viewModel.getMoviesByGenre(extraGenreId)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.errorMsg.observe(this) {
            showError(it)
        }

        viewModel.listMovieByGenre.observe(this) {
            setData(it.results)
        }


    }

    private fun setData(list: List<ResultsItem?>?) {
        movieByGenrePagerAdapter = MovieByGenrePagerAdapter()
        movieByGenrePagerAdapter.submitList(list)
        binding.recyclerView.adapter = movieByGenrePagerAdapter
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