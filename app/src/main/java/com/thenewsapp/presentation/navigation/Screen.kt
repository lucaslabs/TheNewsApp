package com.thenewsapp.presentation.navigation

/**
 * Screen model for navigation purpose.
 */
sealed class Screen(val route: String) {
    object SearchNews : Screen("search_news")
    object NewsDetail : Screen("news_detail")
}
