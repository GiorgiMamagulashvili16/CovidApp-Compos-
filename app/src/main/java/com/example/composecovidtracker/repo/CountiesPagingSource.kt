package com.example.composecovidtracker.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.composecovidtracker.model.ApiResponse
import com.example.composecovidtracker.network.ApiService

class CountiesPagingSource(private val api: ApiService) : PagingSource<Int, ApiResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ApiResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiResponse> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.getInfo()
            val responseData = mutableListOf<ApiResponse>()
            val data = response.body() ?: emptyList()

            responseData.addAll(data)
            val prevKey = if (currentPage == 1) null else currentPage - 1
            val nextKey = if (data.isEmpty()) null else currentPage + 1
            return LoadResult.Page(
                responseData, prevKey, nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}