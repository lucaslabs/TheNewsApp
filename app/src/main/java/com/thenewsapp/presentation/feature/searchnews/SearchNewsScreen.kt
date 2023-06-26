package com.thenewsapp.presentation.feature.searchnews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
        newsList = newsList,
        onSearch = { },
        onNewsClick = { },
        onNavigateToNewsDetailScreen = { }
    )
}

@Composable
fun SearchNewsScreen(
    viewModel: SearchNewsViewModel,
    onNavigateToNewsDetailScreen: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val result = state) {
        is SearchNewsUiState.Idle -> {
            SearchNewsContent(
                onSearch = { query ->
                    viewModel.searchNews(query)
                },
                onNewsClick = { },
                onNavigateToNewsDetailScreen = { }
            )
        }

        is SearchNewsUiState.Loading -> ShowLoadingIndicator()
        is SearchNewsUiState.Success -> {
            SearchNewsContent(
                newsList = result.news,
                onSearch = { },
                onNewsClick = { news ->
                    viewModel.selectedNews.value = news
                },
                onNavigateToNewsDetailScreen = {
                    onNavigateToNewsDetailScreen()
                }
            )
        }

        is SearchNewsUiState.Error -> ShowError(error = result.throwable.message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsContent(
    newsList: List<News> = emptyList(),
    onSearch: (String) -> Unit,
    onNewsClick: (News) -> Unit,
    onNavigateToNewsDetailScreen: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChange = { query = it },
            onSearch = { query ->
                active = false
                if (query.isNotEmpty()) {
                    onSearch(query)
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
                        if (query.isNotEmpty()) {
                            onSearch(query)
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = newsList) { news ->
                NewsItem(
                    news = news,
                    onNewsClick = {
                        onNewsClick(news)
                        onNavigateToNewsDetailScreen()
                    }
                )
            }
        }
    }
}

@Composable
fun ShowLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}

@Composable
fun ShowError(error: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            text = error ?: stringResource(id = R.string.error_generic),
        )
    }
}