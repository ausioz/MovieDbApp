package com.example.moviedbapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.ViewModelFactory
import com.example.moviedbapp.data.response.GenresItem
import com.example.moviedbapp.databinding.ActivityMainBinding
import com.example.moviedbapp.ui.MovieViewModel
import com.example.moviedbapp.ui.cutomview.LoadingDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MovieViewModel> {
        ViewModelFactory.getInstance()
    }

    private lateinit var mainPagerAdapter: MainPagerAdapter

    private val loadingDialog = LoadingDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        viewModel.getGenre()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.errorMsg.observe(this) {
            showError(it)
        }

        viewModel.listGenre.observe(this) {
            setData(it.genres)
        }

    }

    private fun setData(list: List<GenresItem>) {
        mainPagerAdapter = MainPagerAdapter()
        mainPagerAdapter.submitList(list)
        binding.recyclerView.adapter = mainPagerAdapter
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