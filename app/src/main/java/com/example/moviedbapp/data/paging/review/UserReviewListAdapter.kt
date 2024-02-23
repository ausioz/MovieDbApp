package com.example.moviedbapp.data.paging.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapp.data.response.ReviewResultsItem
import com.example.moviedbapp.databinding.ItemReviewBinding

class UserReviewListAdapter :
    PagingDataAdapter<ReviewResultsItem, UserReviewListAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewResultsItem) {
            binding.tvUser.text = review.author
            binding.tvReview.text = review.content
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
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
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