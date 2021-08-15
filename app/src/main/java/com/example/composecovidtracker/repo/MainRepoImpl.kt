package com.example.composecovidtracker.repo

import com.example.composecovidtracker.model.ApiResponse
import com.example.composecovidtracker.network.ApiService
import com.example.composecovidtracker.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val coronaApi: ApiService
) : MainRepo {
    override suspend fun getAllCountry(): Resource<List<ApiResponse>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = coronaApi.getInfo()
            if (response.isSuccessful) {
                val body = response.body()
                Resource.Success(body!!)
            } else {
                Resource.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }

    override suspend fun getDetailInfo(country: String): Resource<ApiResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = coronaApi.getDetailCountryInfo(country)
                if (response.isSuccessful) {
                    val body = response.body()
                    Resource.Success(body!!)
                } else {
                    Resource.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                Resource.Error(e.toString())
            }
        }
}