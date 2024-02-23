package com.example.moviedbapp.ui.details

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapp.data.response.GenresItem
import com.example.moviedbapp.data.response.ReviewResultsItem
import com.example.moviedbapp.databinding.ItemGenreBinding
import com.example.moviedbapp.databinding.ItemReviewBinding
import com.example.moviedbapp.ui.movielist.MovieByGenreActivity


class ReviewPagerAdapter : ListAdapter<ReviewResultsItem, ReviewPagerAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewResultsItem) {
            binding.tvUser.text = review.author
            binding.tvReview.text = review.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewResultsItem>() {
            override fun areItemsTheSame(
                oldItem: ReviewResultsItem, newItem: ReviewResultsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ReviewResultsItem, newItem: ReviewResultsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}