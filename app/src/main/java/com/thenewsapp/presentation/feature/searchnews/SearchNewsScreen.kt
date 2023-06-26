package com.thenewsapp.presentation.feature.searchnews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thenewsapp.R
import com.thenewsapp.data.model.News

@Preview(showBackground = true)
@Composable
fun SearchNewsScreenPreview(
    @PreviewParameter(NewsPreviewParameterProvider::class) newsList: List<News>
) {
    SearchNewsContent(
        state = SearchNewsUiState.Success(newsList),
        onSearchNews = { },
        onNewsClick = { },
        onNavigateToNewsDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
fun ShowEmptyStatePreview() {
    SearchNewsContent(
        state = SearchNewsUiState.Success(emptyList()),
    )
}

@Preview(showBackground = true)
@Composable
fun ShowLoadingPreview() {
    SearchNewsContent(
        state = SearchNewsUiState.Loading
    )
}

@Preview(showBackground = true)
@Composable
fun ShowErrorPreview() {
    SearchNewsContent(
        state = SearchNewsUiState.Error(Throwable("Error message"))
    )
}

@Composable
fun SearchNewsScreen(
    viewModel: SearchNewsViewModel,
    onNavigateToNewsDetailScreen: () -> Unit
) {
    val state: SearchNewsUiState by viewModel.state.collectAsStateWithLifecycle()

    SearchNewsContent(
        state = state,
        onSearchNews = { query ->
            viewModel.searchNews(query)
        },
        onNewsClick = { news ->
            viewModel.selectedNews.value = news
        },
        onNavigateToNewsDetailScreen = {
            onNavigateToNewsDetailScreen()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsContent(
    state: SearchNewsUiState,
    onSearchNews: (String) -> Unit = { },
    onNewsClick: (News) -> Unit = { },
    onNavigateToNewsDetailScreen: () -> Unit = { }
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            query = query,
            onQueryChange = { query = it },
            onSearch = { query ->
                active = false
                if (query.isNotEmpty()) {
                    onSearchNews(query)
                }
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search)
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        active = false
                        if (query.isNotEmpty()) {
                            onSearchNews(query)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) {
                                query = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            },
        ) { }

        when (val result = state) {
            is SearchNewsUiState.Idle -> {
                // no-op
            }

            is SearchNewsUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center)
                )
            }

            is SearchNewsUiState.Success -> {
                if (result.news.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 64.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopStart),
                        state = rememberLazyListState(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = result.news) { news ->
                            NewsItem(
                                news = news,
                                onNewsClick = {
                                    onNewsClick(news)
                                    onNavigateToNewsDetailScreen()
                                }
                            )
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.no_results_found),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2
                    )
                }
            }

            is SearchNewsUiState.Error -> {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = result.throwable.message ?: stringResource(id = R.string.generic_error),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2
                )
            }
        }
    }
}