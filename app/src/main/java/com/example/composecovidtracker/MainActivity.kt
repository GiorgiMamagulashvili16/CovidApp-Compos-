package com.example.composecovidtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.composecovidtracker.ui.screens.CountryListScreen
import com.example.composecovidtracker.ui.screens.DetailScreen
import com.example.composecovidtracker.ui.theme.ComposeCovidTrackerTheme
import com.example.composecovidtracker.util.Constants.COLOR_KEY
import com.example.composecovidtracker.util.Constants.COUNTRY_NAME_KEY
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

typealias drawable = R.drawable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCovidTrackerTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "country_list_screen") {
                    composable("country_list_screen") {
                        CountryListScreen(navController = navController)
                    }
                    composable("detail_screen/{$COUNTRY_NAME_KEY}",
                        arguments = listOf(
                            navArgument(COUNTRY_NAME_KEY) {
                                type = NavType.StringType
                            }
                        )) { nav ->
                        val countryName = remember {
                            nav.arguments?.getString(COUNTRY_NAME_KEY)
                        }
                        DetailScreen(navController = navController, countryName = countryName!!.lowercase(
                            Locale.getDefault()
                        ))
                    }
                }
            }
        }
    }
}
