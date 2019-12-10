package com.omermuhammed.nytmoviereviews

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.omermuhammed.nytmoviereviews.data.MovieReviewsRoomDb
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

abstract class MovieReviewsRoomDbTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: MovieReviewsRoomDb
    val db: MovieReviewsRoomDb get() = _db

    @Before
    fun initDb() {
        _db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieReviewsRoomDb::class.java
        ).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}
