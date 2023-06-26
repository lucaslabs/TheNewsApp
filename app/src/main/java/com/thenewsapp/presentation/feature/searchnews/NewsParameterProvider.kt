package com.thenewsapp.presentation.feature.searchnews

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.thenewsapp.domain.model.News

/**
 * Provides sample values of list of news to use in preview composable functions.
 */
class NewsPreviewParameterProvider : PreviewParameterProvider<List<News>> {
    override val values: Sequence<List<News>> =
        sequenceOf(
            listOf(
                News(
                    author = "Main author",
                    title = "This is a long looong looooong long looong looooong long looong looooong title",
                    description = "",
                    url = "",
                    urlToImage = "",
                    content = ""
                ),
                News(
                    author = "Another author",
                    title = "Another long looong looooong long looong looooong long looong looooong title",
                    description = "",
                    url = "",
                    urlToImage = "",
                    content = ""
                ),
                News(
                    author = "The author",
                    title = "The long looong looooong long looong looooong long looong looooong title",
                    description = "",
                    url = "",
                    urlToImage = "",
                    content = ""
                )
            )
        )

}