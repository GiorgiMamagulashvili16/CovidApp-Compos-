package com.example.composecovidtracker.network

import com.example.composecovidtracker.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/v3/covid-19/countries")
    suspend fun getInfo(): Response<List<ApiResponse>>

    @GET("/v3/covid-19/countries/{country}")
    suspend fun getDetailCountryInfo(
        @Path("country") country: String,
        @Query("strict") strict: Boolean = true
    ): Response<ApiResponse>
}