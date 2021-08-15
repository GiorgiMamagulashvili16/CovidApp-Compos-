package com.example.composecovidtracker.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecovidtracker.model.ApiResponse
import com.example.composecovidtracker.repo.MainRepoImpl
import com.example.composecovidtracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mainRepoImpl: MainRepoImpl
) : ViewModel() {

    suspend fun getDetails(countryName: String): Resource<ApiResponse> {
        return mainRepoImpl.getDetailInfo(countryName)
    }
}