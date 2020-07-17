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
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.BDDMockito.*
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

    private lateinit var viewModel: SharedNewsViewModel

    private val VALID_QUERY = "android"
    private val NOT_VALID_QUERY = "Lorem ipsum"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SharedNewsViewModel(newsService)

        // Observe the LiveData forever
        viewModel.news.observeForever(observer)
    }

    @After
    fun tearDown() {
        // Whatever happens, remove the observer!
        viewModel.news.removeObserver(observer)
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

        // When
        viewModel.searchNews(VALID_QUERY)
        val actualNews = viewModel.news.value

        // Then
        assertThat(expectedNews, equalTo(actualNews?.data))

        verify(observer, times(1)).onChanged(isA(Resource.Loading::class.java))
        verify(observer, times(1)).onChanged(isA(Resource.Success::class.java))
        verify(observer, never()).onChanged(isA(Resource.Error::class.java))

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `search news with a not valid query should return error event`() {
        // Given
        val expectedError = Throwable("Error message")
        val mockErrorResponse = Calls.failure<NewsResponse>(expectedError)
        given(newsService.getNews(NOT_VALID_QUERY)).willReturn(mockErrorResponse)

        // When
        viewModel.searchNews(NOT_VALID_QUERY)
        val actualError = viewModel.news.value

        // Then
        assertThat(expectedError.message, equalTo(actualError?.message))

        verify(observer, times(1)).onChanged(isA(Resource.Loading::class.java))
        verify(observer, times(1)).onChanged(isA(Resource.Error::class.java))
        verify(observer, never()).onChanged(isA(Resource.Success::class.java))

        verifyNoMoreInteractions(observer)
    }
}