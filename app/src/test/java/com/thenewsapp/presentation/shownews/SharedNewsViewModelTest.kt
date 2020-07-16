package com.thenewsapp.presentation.shownews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.NewsResponse
import com.thenewsapp.data.net.model.Resource
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.mock.Calls

@RunWith(JUnit4::class)
class SharedNewsViewModelTest {

    // This rule allows us to run LiveData synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<Resource<*>>

    @Mock
    lateinit var newsService: NewsService

    @Captor
    lateinit var argumentCaptor: ArgumentCaptor<Resource<*>>

    private lateinit var viewModel: SharedNewsViewModel

    private val VALID_QUERY = "android"
    private val NOT_VALID_QUERY = "Lorem ipsum"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SharedNewsViewModel(newsService)
    }

    @Test
    fun `sample test for LiveData`() {
        // Given
        val mutableLiveData = MutableLiveData<String>()

        // When
        mutableLiveData.postValue("test")

        // Then
        assertEquals("test", mutableLiveData.value)
    }

    /**
     * Test naming convention
     * 1. subjectUnderTest_actionOrInput_resultState
     * 2. `subject under test with action or input should return result state`
     */
    @Test
    fun `search news with a valid query should return success event`() {
        // Given
        val expectedNews = arrayListOf<News>()
        val mockNewsResponse = NewsResponse(expectedNews)
        val mockSuccessResponse = Calls.response(mockNewsResponse)
        given(newsService.getNews(VALID_QUERY)).willReturn(mockSuccessResponse)

        try {
            // Observe the LiveData forever
            viewModel.news.observeForever(observer)

            // When
            viewModel.searchNews(VALID_QUERY)

            // Then
            argumentCaptor.run {
                verify(observer, times(2)).onChanged(capture())
                assertThat(allValues[0], instanceOf(Resource.Loading::class.java))
                assertThat(allValues[1], instanceOf(Resource.Success::class.java))
                assertThat(expectedNews, equalTo(value.data))
            }
        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.news.removeObserver(observer)
        }
    }

    @Test
    fun `search news with a not valid query should return error event`() {
        // Given
        val expectedError = Throwable("Error message")
        val mockErrorResponse = Calls.failure<NewsResponse>(expectedError)
        given(newsService.getNews(NOT_VALID_QUERY)).willReturn(mockErrorResponse)

        try {
            // Observe the LiveData forever
            viewModel.news.observeForever(observer)

            // When
            viewModel.searchNews(NOT_VALID_QUERY)

            // Then
            argumentCaptor.run {
                verify(observer, times(2)).onChanged(capture())
                assertThat(allValues[0], instanceOf(Resource.Loading::class.java))
                assertThat(allValues[1], instanceOf(Resource.Error::class.java))
                assertThat(expectedError.message, equalTo(value.message))
            }
        } finally {
            // Whatever happens, don't forget to remove the observer!
            viewModel.news.removeObserver(observer)
        }
    }
}