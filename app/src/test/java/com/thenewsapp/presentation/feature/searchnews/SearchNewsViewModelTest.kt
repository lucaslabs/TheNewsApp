package com.thenewsapp.presentation.feature.searchnews

import app.cash.turbine.test
import com.thenewsapp.domain.SearchNewsUseCase
import com.thenewsapp.domain.model.News
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

/**
 * Unit test cases for [SearchNewsViewModel].
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchNewsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var searchNewsUseCase: SearchNewsUseCase

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
        private const val ERROR_MESSAGE = "Error message"
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SearchNewsViewModel(searchNewsUseCase)
    }

    @Test
    fun `search news should show loading`() = runTest {
        // Given
        every { searchNewsUseCase(RESULT_QUERY) } returns emptyFlow()

        // When
        viewModel.searchNews(RESULT_QUERY)

        // Then
        viewModel.state.test {
            val actualItem: SearchNewsUiState = awaitItem()
            assertTrue(actualItem.isLoading)
        }
    }

    @Test
    fun `search news with a valid query should show a list of news`() = runTest {
        // Given
        val expectedNews = listOf(news)
        every { searchNewsUseCase(RESULT_QUERY) } returns flowOf(expectedNews)

        // When
        viewModel.searchNews(RESULT_QUERY)

        // Then
        viewModel.state.test {
            val actualItem: SearchNewsUiState = awaitItem()
            assertTrue(actualItem.news.isNotEmpty())
        }
    }

    @Test
    fun `search news with a valid query and empty result should show empty state`() = runTest {
        // Given
        every { searchNewsUseCase(NO_RESULT_QUERY) } returns flowOf(emptyList())

        // When
        viewModel.searchNews(NO_RESULT_QUERY)

        // Then
        viewModel.state.test {
            val actualItem: SearchNewsUiState = awaitItem()
            assertTrue(actualItem.news.isEmpty())
        }
    }

    @Test
    fun `search news not valid query should show error`() = runTest {
        // Given
        every { searchNewsUseCase(ERROR_QUERY) } returns flow { throw IllegalStateException(ERROR_MESSAGE) }

        // When
        viewModel.searchNews(ERROR_QUERY)

        // Then
        viewModel.state.test {
            val actualItem: SearchNewsUiState = awaitItem()
            assertTrue(actualItem.error == ERROR_MESSAGE)
        }
    }
}