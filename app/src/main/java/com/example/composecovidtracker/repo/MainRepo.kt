package com.example.composecovidtracker.repo

import com.example.composecovidtracker.model.ApiResponse
import com.example.composecovidtracker.util.Resource

interface MainRepo {

    suspend fun getAllCountry(): Resource<List<ApiResponse>>
    suspend fun getDetailInfo(country: String): Resource<ApiResponse>
}