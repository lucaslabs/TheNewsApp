package com.thenewsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thenewsapp.presentation.feature.newsdetail.NewsDetailScreen
import com.thenewsapp.presentation.feature.searchnews.SearchNewsScreen
import com.thenewsapp.presentation.feature.searchnews.SearchNewsViewModel

/**
 * Represents the different navigation routes of the app.
 */
@Composable
fun NewsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.SearchNews.route
) {
    val viewModel : SearchNewsViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.SearchNews.route) {
            SearchNewsScreen(
                viewModel = viewModel,
                onNavigateToNewsDetailScreen = {
                    navController.navigate(Screen.NewsDetail.route)
                }
            )
        }
        composable(route = Screen.NewsDetail.route) {
            NewsDetailScreen(
                viewModel = viewModel
            )
        }
    }
}