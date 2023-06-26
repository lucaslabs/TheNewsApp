package com.thenewsapp.presentation.feature.newsdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.thenewsapp.R
import com.thenewsapp.domain.model.News
import com.thenewsapp.presentation.feature.searchnews.SearchNewsViewModel
import com.thenewsapp.presentation.theme.dimenLarge
import com.thenewsapp.presentation.theme.dimenMedium

@Preview(showBackground = true)
@Composable
fun NewsDetailPreview() {
    NewsDetailContent(
        selectedNews = News(
            author = "Main author",
            title = "This is a long looong looooong long looong looooong long looong looooong title",
            description = "This is a long looong looooong long looong looooong long looong looooong description",
            url = "www.someurl.com",
            urlToImage = "",
            content = ""
        )
    )
}

/**
 * Detail view of a news item.
 */
@Composable
fun NewsDetailScreen(
    viewModel: SearchNewsViewModel
) {
    val selectedNews by viewModel.selectedNews.collectAsStateWithLifecycle()

    NewsDetailContent(selectedNews = selectedNews)
}

@Composable
fun NewsDetailContent(
    selectedNews: News?
) {
    selectedNews?.let {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(dimenLarge),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimenMedium
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimenMedium, vertical = dimenMedium)

            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = it.urlToImage,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.ic_news_placeholder),
                    error = painterResource(id = R.drawable.ic_news_placeholder)
                )

                Text(
                    modifier = Modifier.padding(dimenMedium),
                    text = it.title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2
                )

                Text(
                    modifier = Modifier.padding(dimenMedium),
                    text = it.author ?: "",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2
                )

                Text(
                    modifier = Modifier.padding(dimenMedium),
                    text = it.description,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )

                val annotatedString = buildAnnotatedString {
                    val str = stringResource(id = R.string.read_more_at, it.url)
                    val startIndex = str.indexOf(":") + 1
                    val endIndex = startIndex + str.length

                    append(str)

                    addStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        start = 0,
                        end = startIndex
                    )

                    addStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                        ),
                        start = startIndex,
                        end = endIndex
                    )

                    addStringAnnotation(
                        tag = "URL",
                        annotation = it.url,
                        start = startIndex,
                        end = endIndex
                    )
                }

                val uriHandler = LocalUriHandler.current

                ClickableText(
                    modifier = Modifier.padding(dimenMedium),
                    text = annotatedString,
                    onClick = {
                        annotatedString.getStringAnnotations("URL", it, it)
                            .firstOrNull()?.let { annotation ->
                                uriHandler.openUri(annotation.item)
                            }
                    }
                )
            }
        }
    }
}
