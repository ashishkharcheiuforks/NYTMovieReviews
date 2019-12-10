package com.omermuhammed.nytmoviereviews.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity

@Dao
interface MovieReviewsDao {
    // We should not need two queries but there no other easy way
    @Query("SELECT * from movie_reviews ORDER BY publication_date DESC")
    suspend fun getAllMovieReviews(): List<MovieReviewsEntity>

    @Query("SELECT * from movie_reviews WHERE mpaa_rating = :rating ORDER BY publication_date DESC")
    suspend fun getMovieReviewsByRating(rating: String): List<MovieReviewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieReview(movieReviews: MovieReviewsEntity)
}