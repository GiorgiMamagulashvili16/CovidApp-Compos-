package com.example.composecovidtracker.di

import com.example.composecovidtracker.network.ApiService
import com.example.composecovidtracker.repo.MainRepo
import com.example.composecovidtracker.repo.MainRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://disease.sh"

    @Provides
    @Singleton
    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideMainRepo(apiService: ApiService): MainRepo = MainRepoImpl(apiService)
}