package com.example.moviedbapp.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviedbapp.R
import com.example.moviedbapp.ViewModelFactory
import com.example.moviedbapp.data.paging.LoadingStateAdapter
import com.example.moviedbapp.data.paging.movie.MovieByGenreListAdapter
import com.example.moviedbapp.data.paging.review.UserReviewListAdapter
import com.example.moviedbapp.databinding.ActivityMovieDetailBinding
import com.example.moviedbapp.ui.MovieViewModel
import com.example.moviedbapp.ui.cutomview.LoadingDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel by viewModels<MovieViewModel> {
        ViewModelFactory.getInstance()
    }
    private lateinit var userReviewListAdapter: UserReviewListAdapter
    private val loadingDialog = LoadingDialogFragment()
    private var extraMovieId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extraMovieId = intent.getIntExtra(MovieByGenreListAdapter.EXTRA_MOVIE, 0)
        val extraMovieName = intent.getStringExtra(MovieByGenreListAdapter.EXTRA_MOVIE_TITLE)

        supportActionBar?.title = extraMovieName

        binding.rvReview.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )


        viewModel.getMovieDetail(extraMovieId)
        viewModel.getVideoKey(extraMovieId)
        getReviewData()


        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.errorMsg.observe(this) {
            showError(it)
        }

        viewModel.movieDetail.observe(this) { detail ->
            Glide.with(this).load(BASE_IMG_URL + detail.posterPath).into(binding.ivPosterDetail)

            Glide.with(this).load(BASE_IMG_URL + detail.backdropPath).into(binding.ivBackdrop)

            binding.tvVoteCount.text = detail.voteCount.toString()
            val voteRating = detail.voteAverage
            val ratingInFloat = String.format("%.1f", voteRating)
            binding.tvRatingResult.text = getString(R.string.rating_in_float, ratingInFloat)
            binding.tvLanguage.text =
                getString(R.string.language, detail.spokenLanguages?.get(0)?.englishName)
            binding.tvDescriptionMS.text = detail.overview
            binding.tvTitleDetail.text = detail.title
            binding.tvRelease.text = getString(R.string.release, detail.releaseDate)
            val genreName = detail.genres?.map { it?.name }?.toTypedArray()
            val sbGenre = StringBuilder()
            sbGenre.append(getString(R.string.genre) + ":\n")
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
                        this@MovieDetailActivity, "Official Trailer not found", Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun getReviewData() {
        userReviewListAdapter = UserReviewListAdapter()
        binding.rvReview.adapter =
            userReviewListAdapter.withLoadStateFooter(footer = LoadingStateAdapter {
                userReviewListAdapter.retry()
            })
        userReviewListAdapter.addLoadStateListener { loadState ->
            binding.rvReview.adapter?.apply {
                if (itemCount <= 0 && !loadState.source.refresh.endOfPaginationReached) {
                    binding.tvNoReview.visibility = View.VISIBLE
                } else {
                    binding.tvNoReview.visibility = View.GONE
                }
            }
        }
        viewModel.getReview(extraMovieId).observe(this) {
            userReviewListAdapter.submitData(lifecycle, it)
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

    companion object {
        private const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"
    }

}