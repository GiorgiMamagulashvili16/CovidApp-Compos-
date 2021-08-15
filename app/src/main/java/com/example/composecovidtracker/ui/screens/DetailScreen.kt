package com.example.composecovidtracker.ui.screens

import android.util.Log.d
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.composecovidtracker.model.ApiResponse
import com.example.composecovidtracker.ui.theme.*
import com.example.composecovidtracker.ui.vm.DetailViewModel
import com.example.composecovidtracker.util.Resource

val colors = mutableListOf(HPColor, AtkColor, DefColor, SpAtkColor, SpDefColor, SpdColor)

@ExperimentalFoundationApi
@Composable
fun DetailScreen(
    navController: NavController,
    countryName: String,
    viewModel: DetailViewModel = hiltViewModel(),
    imageSize: Dp = 140.dp,
    topPadding: Dp = 20.dp
) {
    val detailInfo = produceState<Resource<ApiResponse>>(initialValue = Resource.Loading()) {
        value = viewModel.getDetails(countryName)
    }.value


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(colors.random(), colors.random())))
    ) {
        Column {
            ScreenTopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
            )
            DetailWrapper(
                details = detailInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.surface)
                    .align(Alignment.CenterHorizontally),
            )
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp), contentAlignment = TopCenter
    ) {
        val painter =
            rememberImagePainter(data = detailInfo.data?.countryInfo?.flag,
                builder = {
                    transformations(
                        CircleCropTransformation()
                    )
                })
        Image(
            painter = painter,
            contentDescription = "image",
            modifier = Modifier
                .size(imageSize)
                .offset(y = topPadding)
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun DetailWrapper(
    details: Resource<ApiResponse>,
    modifier: Modifier = Modifier,
) {
    when (details) {
        is Resource.Success -> {

            val info = details.data
            d("infoinfoinfo", "$info")
            val statisticMap = mutableMapOf<String, Int>(
                "Total Cases" to info?.cases!!,
                "ToTal Deaths" to info.deaths!!,
                "Total Recovered" to info.recovered!!,
                "Active Cases" to info.active!!,
                "Tests" to info.tests!!
            )
            ScreenDetailSection(
                details = details.data,
                modifier = modifier,
                statisticMap
            )
        }
        is Resource.Error -> {

        }
        is Resource.Loading -> {

        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ScreenDetailSection(
    details: ApiResponse,
    modifier: Modifier = Modifier,
    statisticsMap: MutableMap<String, Int>
) {
    Card(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 40.dp)

        ) {
            Text(
                text = details.country!!,
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colors.onSurface,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 7.dp, end = 7.dp, bottom = 7.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(statisticsMap.keys.size) {
                    StatisticItem(
                        title = statisticsMap.keys.toList()[it],
                        statistic = statisticsMap.values.toList()[it]
                    )
                }
            }
        }
    }
}

@Composable
fun StatisticItem(
    title: String,
    statistic: Int
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1.5f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)

        ) {
            Text(
                text = title,
                fontSize = 21.sp,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(TopStart),
            )
            Text(
                text = statistic.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .align(TopStart)
                    .offset(y = 45.dp)
            )
        }
    }

}

@Composable
fun ScreenTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

    }
}
