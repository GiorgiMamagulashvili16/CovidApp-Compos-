package com.example.composecovidtracker.ui.screens

import android.util.Log.d
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.composecovidtracker.drawable
import com.example.composecovidtracker.model.ApiResponse
import com.example.composecovidtracker.ui.theme.*
import com.example.composecovidtracker.ui.vm.CountryScreenViewModel

@ExperimentalCoilApi
@Composable
fun CountryListScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(45.dp))
            Image(
                painter = painterResource(id = drawable.ic_covid_19),
                contentDescription = "covid",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(30.dp))
            CountriesList(navController = navController)
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CountriesList(
    navController: NavController,
    viewModel: CountryScreenViewModel = hiltViewModel()
) {

    val countryList = viewModel.countryList.value
    val errorMessage = viewModel.errorMessage.value
    val isLoading = viewModel.isLoading.value

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (countryList.size % 2 == 0) countryList.size / 2 else {
            countryList.size / 2 + 1
        }
        items(itemCount) {
            CountryRow(rowIndex = it, countries = countryList, navController = navController)
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (errorMessage.isNotEmpty()) {
            RetryLoad(errorMes = errorMessage) {
                viewModel.loadCountries()
            }
        }
    }
}

@Composable
fun RetryLoad(
    errorMes: String,
    onRetry: () -> Unit
) {
    Column {
        Text(text = errorMes, color = Color.Red, fontSize = 21.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CountriesEntry(
    country: ApiResponse,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val colors = mutableListOf(HPColor, AtkColor, DefColor, SpAtkColor, SpDefColor, SpdColor)

    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
              MaterialTheme.colors.surface
            )
            .clickable {
                navController.navigate(
                    "detail_screen/${country.country}"
                )
            }
    ) {
        Column {
            val painter = rememberImagePainter(data = country.countryInfo.flag!!)
            val painterState = painter.state
            Image(
                painter = painter,
                contentDescription = "image",
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)

            )
            if (painterState is ImagePainter.State.Loading) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
            Text(
                text = country.country!!,
                fontSize = 21.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CountryRow(
    rowIndex: Int,
    countries: List<ApiResponse>,
    navController: NavController
) {
    Column {
        Column {
            Row {
                CountriesEntry(
                    country = countries[rowIndex * 2],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                if (countries.size >= rowIndex * 2 + 2) {
                    CountriesEntry(
                        country = countries[rowIndex * 2 + 1],
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}