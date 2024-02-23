package com.example.moviedbapp.data.paging.movie

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedbapp.data.remote.ApiService
import com.example.moviedbapp.data.response.ResultsItem

class MovieByGenrePagingSource(private val apiService: ApiService, private val genreId: Int) :
    PagingSource<Int, ResultsItem>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val position = params.key ?: 1
            val responseData = apiService.getMoviesByGenre(genreId, position)
            LoadResult.Page(
                data = responseData.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (responseData.results.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            Log.d("MovieByGenrePagingSource", "load: $exception")
            return LoadResult.Error(exception)
        }
    }

}