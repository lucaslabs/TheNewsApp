package com.thenewsapp.presentation.feature.searchnews

import app.cash.turbine.test
import com.thenewsapp.data.model.News
import com.thenewsapp.domain.GetNewsUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchNewsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var getNewsUseCase: GetNewsUseCase

    private lateinit var viewModel: SearchNewsViewModel

    private val news = News(
        author = "Main author",
        title = "This is a long looong looooong long looong looooong long looong looooong title",
        description = "",
        url = "",
        urlToImage = "",
        content = ""
    )

    companion object {
        private const val RESULT_QUERY = "android"
        private const val NO_RESULT_QUERY = "lorem ipsum"
        private const val ERROR_QUERY = "error"
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SearchNewsViewModel(getNewsUseCase)
    }

    @Test
    fun `search news should show loading`() = runTest {
        // Given
        every { getNewsUseCase(RESULT_QUERY) } returns emptyFlow()

        // When
        viewModel.searchNews(RESULT_QUERY)

        // Then
        viewModel.state.test {
            assertTrue(awaitItem() is SearchNewsUiState.Loading)
        }
    }

    @Test
    fun `search news with a valid query should show a list of news`() = runTest {
        // Given
        val expectedNews = listOf(news)
        every { getNewsUseCase(RESULT_QUERY) } returns flowOf(expectedNews)

        // When
        viewModel.searchNews(RESULT_QUERY)

        // Then
        viewModel.state.test {
            val actualItem = awaitItem()
            assertTrue(actualItem is SearchNewsUiState.Success)
            assertTrue((actualItem as SearchNewsUiState.Success).news.isNotEmpty())
        }
    }

    @Test
    fun `search news with a valid query and empty result should show empty state`() = runTest {
        // Given
        every { getNewsUseCase(NO_RESULT_QUERY) } returns flowOf(emptyList())

        // When
        viewModel.searchNews(NO_RESULT_QUERY)

        // Then
        viewModel.state.test {
            val actualItem = awaitItem()
            assertTrue(actualItem is SearchNewsUiState.Success)
            assertTrue((actualItem as SearchNewsUiState.Success).news.isEmpty())
        }
    }

    @Test
    fun `search news not valid query should show error`() = runTest {
        // Given
        every { getNewsUseCase(ERROR_QUERY) } returns flow { throw IllegalStateException("Error message") }

        // When
        viewModel.searchNews(ERROR_QUERY)

        // Then
        viewModel.state.test {
            val actualItem: SearchNewsUiState = awaitItem()
            assertTrue(actualItem is SearchNewsUiState.Error)
            assertTrue((actualItem as SearchNewsUiState.Error).throwable.message == "Error message")
        }
    }
}