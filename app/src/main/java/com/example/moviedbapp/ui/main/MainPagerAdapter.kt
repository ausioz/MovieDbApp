package com.example.moviedbapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapp.data.response.GenresItem
import com.example.moviedbapp.databinding.ItemGenreBinding
import com.example.moviedbapp.ui.movielist.MovieByGenreActivity


class MainPagerAdapter : ListAdapter<GenresItem, MainPagerAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenresItem) {
            binding.tvGenre.text = genre.name

            binding.cardGenre.setOnClickListener {
                val intent = Intent(itemView.context, MovieByGenreActivity::class.java)
                intent.putExtra(EXTRA_GENRE, genre.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        const val EXTRA_GENRE = "genreId"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenresItem>() {
            override fun areItemsTheSame(
                oldItem: GenresItem, newItem: GenresItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GenresItem, newItem: GenresItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}