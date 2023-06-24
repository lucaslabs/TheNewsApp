package com.thenewsapp.presentation.shownews

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SharedNewsViewModelTest {

//    // This rule allows us to run LiveData synchronously
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private val testCoroutineDispatcher = TestCoroutineDispatcher()
//
//    @Mock
//    lateinit var getNewsUseCase: GetNewsUseCase
//
//    @Mock
//    lateinit var saveQueryUseCase: SaveQueryUseCase
//
//    private lateinit var viewModel: SharedNewsViewModel // SUT
//
//    private val TEST_VALUE = "test"
//    private val VALID_QUERY = "android"
//    private val EMPTY_QUERY = ""
//    private val NOT_VALID_QUERY = "Lorem ipsum"
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        viewModel = SharedNewsViewModel(null, getNewsUseCase, saveQueryUseCase)
//
//        // Sets the given [dispatcher] as an underlying dispatcher of [Dispatchers.Main]
//        Dispatchers.setMain(testCoroutineDispatcher)
//    }
//
//    @After
//    fun tearDown() {
//        // Resets state of the [Dispatchers.Main] to the original main dispatcher
//        Dispatchers.resetMain()
//
//        // Clean up the TestCoroutineDispatcher to make sure no other work is running
//        testCoroutineDispatcher.cleanupTestCoroutines()
//    }
//
//
//    suspend fun foo() {
//        delay(1000)
//        println("foo")
//    }
//
//
//    @Test
//    fun `test delay in coroutine`() = runBlockingTest {
//        val realStartTime = System.currentTimeMillis()
//        val virtualStartTime = currentTime
//
//        foo()
//
//        println("Unit test time: ${System.currentTimeMillis() - realStartTime} ms")
//        println("Real call time: ${currentTime - virtualStartTime} ms")
//    }
//
//    @Test
//    fun `test live data`() {
//        // Given
//        val mutableLiveData = MutableLiveData<String>()
//
//        // When
//        mutableLiveData.value = TEST_VALUE
//
//        // Then
//        assertThat(mutableLiveData.value, equalTo(TEST_VALUE))
//    }
//
//    /**
//     * Test naming convention:
//     * 1. subjectUnderTest_actionOrInput_resultState
//     * 2. `subject under test with action or input should return result state`
//     */
//    @Test
//    fun `search news with a valid query should return success event`() = runBlockingTest {
//        // Given
//        val expectedNews = arrayListOf<News>()
//        val mockResponse = NewsResponse(expectedNews)
//        given(getNewsUseCase(VALID_QUERY)).willReturn(mockResponse)
//
//        // When
//        val liveDataResponse = viewModel.searchNews(VALID_QUERY)
//
//        // Then
//        val loading = liveDataResponse.getOrAwaitValue()
//        assertThat(loading, instanceOf(Result.Loading::class.java))
//
//        val success = liveDataResponse.getOrAwaitValue()
//        assertThat(success, instanceOf(Result.Success::class.java))
//        assertThat((success as Result.Success).data, equalTo(expectedNews))
//    }
//
//    @Test
//    fun `search news with a not valid query should return error event`() = runBlockingTest {
//        // Given
//        `when`(getNewsUseCase(NOT_VALID_QUERY)).thenThrow(RuntimeException())
//
//        // When
//        val liveDataResponse = viewModel.searchNews(NOT_VALID_QUERY)
//
//        // Then
//        val loading = liveDataResponse.getOrAwaitValue()
//        assertThat(loading, instanceOf(Result.Loading::class.java))
//
//        val error = liveDataResponse.getOrAwaitValue()
//        assertThat(error, instanceOf(Result.Error::class.java))
//    }
//
//    @Test
//    fun `search news with an empty query should return null event`() = runBlockingTest {
//        // Given
//        given(getNewsUseCase(EMPTY_QUERY)).willReturn(null)
//
//        // When
//        val liveDataResponse = viewModel.searchNews(EMPTY_QUERY)
//
//        // Then
//        assertNull(liveDataResponse.value)
//    }
}