package com.thenewsapp.presentation.shownews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.NewsResponse
import com.thenewsapp.data.net.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
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
import retrofit2.Response

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class SharedNewsViewModelTest {

    // This rule allows us to run LiveData synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var observer: Observer<Resource<*>>

    @Mock
    lateinit var newsService: NewsService

    private lateinit var viewModel: SharedNewsViewModel

    private val VALID_QUERY = "android"
    private val NOT_VALID_QUERY = "Lorem ipsum"
    private val NOT_FOUND_ERROR_CODE = 404

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SharedNewsViewModel(newsService)

        // Observe the LiveData forever
        viewModel.news.observeForever(observer)

        // Sets the given [dispatcher] as an underlying dispatcher of [Dispatchers.Main]
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        // Whatever happens, remove the observer!
        viewModel.news.removeObserver(observer)

        // Resets state of the [Dispatchers.Main] to the original main dispatcher
        Dispatchers.resetMain()

        // Clean up the TestCoroutineDispatcher to make sure no other work is running
        testCoroutineDispatcher.cleanupTestCoroutines()
    }


    suspend fun foo() {
        delay(1000)
        println("foo")
    }


    @Test
    fun `test delay in coroutine`() = runBlockingTest {
        val realStartTime = System.currentTimeMillis()
        val virtualStartTime = currentTime

        foo()

        println("Unit test time: ${System.currentTimeMillis() - realStartTime} ms")
        println("Real call time: ${currentTime - virtualStartTime} ms")
    }

    @Test
    fun `test live data`() {
        // Given
        val mutableLiveData = MutableLiveData<String>()

        // When
        mutableLiveData.postValue("test")

        // Then
        assertThat(mutableLiveData.value, equalTo("test"))
    }

    /**
     * Test naming convention
     * 1. subjectUnderTest_actionOrInput_resultState
     * 2. `subject under test with action or input should return result state`
     */
    @Test
    fun `search news with a valid query should return success event`() = runBlockingTest {
        // Given
        val expectedNews = arrayListOf<News>()
        val mockResponse = NewsResponse(expectedNews)
        val mockSuccessResponse = Response.success(mockResponse)
        given(newsService.searchNews(VALID_QUERY)).willReturn(mockSuccessResponse)

        // When
        viewModel.searchNews(VALID_QUERY)
        val actualNews = viewModel.news.value

        // Then
        assertThat(actualNews?.data, equalTo(expectedNews))

        verify(observer, times(1)).onChanged(isA(Resource.Loading::class.java))
        verify(observer, times(1)).onChanged(isA(Resource.Success::class.java))
        verify(observer, never()).onChanged(isA(Resource.Error::class.java))

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `search news with a not valid query should return error event`() = runBlockingTest {
        // Given
        val expectedError = "Response.error()"
        val mockResponseBody = mock(ResponseBody::class.java)
        val mockErrorResponse =
            Response.error<NewsResponse>(NOT_FOUND_ERROR_CODE, mockResponseBody)
        given(newsService.searchNews(NOT_VALID_QUERY)).willReturn(mockErrorResponse)

        // When
        viewModel.searchNews(NOT_VALID_QUERY)
        val actualError = viewModel.news.value

        // Then
        assertThat(actualError?.message, equalTo(expectedError))

        verify(observer, times(1)).onChanged(isA(Resource.Loading::class.java))
        verify(observer, times(1)).onChanged(isA(Resource.Error::class.java))
        verify(observer, never()).onChanged(isA(Resource.Success::class.java))

        verifyNoMoreInteractions(observer)
    }
}