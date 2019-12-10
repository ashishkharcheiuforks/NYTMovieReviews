package com.omermuhammed.nytmoviereviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.omermuhammed.nytmoviereviews.util.TestUtil
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieReviewsDaoTest : MovieReviewsRoomDbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val movieReviewOne = TestUtil.createMovieReviewEntity(
        "Test Title One",
        "PG",
        "Test Headline One",
        "Test summary One",
        "Test Byline One",
        TestUtil.createMultimedia("type One", "src One", 100, 100),
        publication_date = "Date String One"
    )

    val movieReviewTwo = TestUtil.createMovieReviewEntity(
        "Test Title Two",
        "PG-13",
        "Test Headline Two",
        "Test summary Two",
        "Test Byline Two",
        TestUtil.createMultimedia("type Two", "src Two", 200, 200),
        publication_date = "Date String"
    )

    @Test
    fun insertAndLoad() = runBlocking {

        db.movieReviewsDao().insertMovieReview(movieReviewOne)
        db.movieReviewsDao().insertMovieReview(movieReviewTwo)

        val allMovieReviewFromDb = db.movieReviewsDao().getAllMovieReviews()
        assertEquals(2, allMovieReviewFromDb.size)
        assertEquals(listOf(movieReviewOne, movieReviewTwo), allMovieReviewFromDb)

        val movieReviewByRating = db.movieReviewsDao().getMovieReviewsByRating("PG")
        assertEquals(listOf(movieReviewOne), movieReviewByRating)

    }
}
