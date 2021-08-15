package com.example.composecovidtracker.ui.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.composecovidtracker.model.ApiResponse
import com.example.composecovidtracker.network.ApiService
import com.example.composecovidtracker.repo.CountiesPagingSource
import com.example.composecovidtracker.repo.MainRepoImpl
import com.example.composecovidtracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryScreenViewModel @Inject constructor(
    private val mainRepoImpl: MainRepoImpl,
    private val apiService: ApiService
) : ViewModel() {

    var countryList = mutableStateOf<List<ApiResponse>>(listOf())
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    init {
        loadCountries()
    }

    fun loadCountries() = viewModelScope.launch {
        isLoading.value = true
        val result = mainRepoImpl.getAllCountry()
        when (result) {
            is Resource.Success -> {
                countryList.value = result.data!!
                isLoading.value = false
            }
            is Resource.Error -> {
                isLoading.value = false
                errorMessage.value = result.errorMessage!!
            }
        }
    }
}