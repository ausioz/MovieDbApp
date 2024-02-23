package com.example.moviedbapp.data.paging.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedbapp.data.response.ResultsItem
import com.example.moviedbapp.databinding.ItemMovieBinding
import com.example.moviedbapp.ui.details.MovieDetailActivity

class MovieByGenreListAdapter :
    PagingDataAdapter<ResultsItem, MovieByGenreListAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: ResultsItem) {
            binding.tvMovie.text = movie.title

            Glide.with(binding.root.context).load(movie.loadPoster()).into(binding.ivMovie)

            binding.cardMovie.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE, movie.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {

        const val EXTRA_MOVIE = "movieId"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResultsItem, newItem: ResultsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}