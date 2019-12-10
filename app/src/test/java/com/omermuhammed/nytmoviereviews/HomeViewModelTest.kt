package com.omermuhammed.nytmoviereviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity
import com.omermuhammed.nytmoviereviews.data.repository.MovieReviewsRepository
import com.omermuhammed.nytmoviereviews.network.Resource
import com.omermuhammed.nytmoviereviews.util.mock
import com.omermuhammed.nytmoviereviews.viewmodel.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

// Still needs some more testing
@RunWith(JUnit4::class)
class HomeViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    private val repository = mock(MovieReviewsRepository::class.java)
    private lateinit var homeViewModel: HomeViewModel

    lateinit var observer: Observer<Resource<List<MovieReviewsEntity>>>

    @Before
    fun init() {
        // need to init after instant executor rule is established.
        homeViewModel = HomeViewModel(repository)
    }

    @Test
    fun basic() = runBlocking {
        assertNotNull(homeViewModel.getMovieReviewsList())
        assertTrue(homeViewModel.getMovieReviewsList().hasObservers())
    }
}