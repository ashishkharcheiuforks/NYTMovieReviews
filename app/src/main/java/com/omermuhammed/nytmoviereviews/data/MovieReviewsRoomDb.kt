package com.omermuhammed.nytmoviereviews.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omermuhammed.nytmoviereviews.data.dao.MovieReviewsDao

@Database(entities = [MovieReviewsEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieReviewsRoomDb : RoomDatabase() {

    abstract fun movieReviewsDao(): MovieReviewsDao
}