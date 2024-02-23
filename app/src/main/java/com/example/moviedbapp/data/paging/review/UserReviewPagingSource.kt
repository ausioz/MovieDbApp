package com.example.moviedbapp.data.paging.review

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedbapp.data.remote.ApiService
import com.example.moviedbapp.data.response.ReviewResultsItem

class UserReviewPagingSource(private val apiService: ApiService, private val movieId: Int) :
    PagingSource<Int, ReviewResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ReviewResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewResultsItem> {
        return try {
            val position = params.key ?: 1
            val responseData = apiService.getReviews(movieId, position)
            LoadResult.Page(
                data = responseData.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (responseData.results.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            Log.d("UserReviewPagingSource", "load: $exception")
            return LoadResult.Error(exception)
        }
    }

}