package com.example.moviedbapp.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviedbapp.ViewModelFactory
import com.example.moviedbapp.data.response.ResultsItem
import com.example.moviedbapp.data.response.ReviewResultsItem
import com.example.moviedbapp.databinding.ActivityMovieDetailBinding
import com.example.moviedbapp.ui.MovieViewModel
import com.example.moviedbapp.ui.cutomview.LoadingDialogFragment
import com.example.moviedbapp.ui.main.MainPagerAdapter
import com.example.moviedbapp.ui.movielist.MovieByGenrePagerAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel by viewModels<MovieViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var reviewPagerAdapter: ReviewPagerAdapter
    private val loadingDialog = LoadingDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraMovieId = intent.getIntExtra(MovieByGenrePagerAdapter.EXTRA_MOVIE, 0)

        binding.rvReview.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )


        viewModel.getMovieDetail(extraMovieId)
        viewModel.getVideoKey(extraMovieId)
        viewModel.getReview(extraMovieId)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.errorMsg.observe(this) {
            showError(it)
        }

        viewModel.movieDetail.observe(this) { detail ->
            Glide.with(this)
                .load(BASE_IMG_URL + detail.posterPath)
                .into(binding.ivPosterDetail)

            Glide.with(this)
                .load(BASE_IMG_URL + detail.backdropPath)
                .into(binding.ivBackdrop)

            binding.tvVoteCount.text = detail.voteCount.toString()
            val voteRating = detail.voteAverage
            val ratingInFloat = String.format("%.1f", voteRating)
            binding.tvRatingResult.text = ": $ratingInFloat"
            binding.tvLanguage.text = "Language : ${detail.spokenLanguages?.get(0)?.englishName}"
            binding.tvDescriptionMS.text = detail.overview
            binding.tvTitleDetail.text = detail.title
            binding.tvRelease.text = "Release : ${detail.releaseDate}"
            val genreName = detail.genres?.map { it?.name }?.toTypedArray()
            val sbGenre = StringBuilder()
            for (i in 0 until (genreName?.size ?: 0)) {
                sbGenre.append(genreName?.get(i).toString() + "\n")
            }
            binding.tvGenre.text = (sbGenre.toString())

        }

        lifecycle.addObserver(binding.ytTrailer)
        viewModel.videoKey.observe(this) {
            binding.ytTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    if (!it.isNullOrEmpty()) {
                        binding.ivBackdrop.visibility = View.GONE
                        youTubePlayer.loadVideo(it, 0F)
                    } else Toast.makeText(
                        this@MovieDetailActivity,
                        "Official Trailer not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        viewModel.movieReview.observe(this) {
            setData(it.results)
        }
    }

    private fun setData(list: List<ReviewResultsItem?>?) {
        reviewPagerAdapter = ReviewPagerAdapter()
        reviewPagerAdapter.submitList(list)
        binding.rvReview.adapter = reviewPagerAdapter
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

    companion object {
        private const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    }

}