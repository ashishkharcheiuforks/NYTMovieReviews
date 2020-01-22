package com.omermuhammed.nytmoviereviews.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omermuhammed.nytmoviereviews.data.dao.MovieReviewsDao
import com.omermuhammed.nytmoviereviews.utils.DB_NAME

@Database(entities = [MovieReviewsEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieReviewsRoomDb : RoomDatabase() {

    abstract fun movieReviewsDao(): MovieReviewsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieReviewsRoomDb? = null

        fun getDatabase(context: Context): MovieReviewsRoomDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieReviewsRoomDb::class.java,
                        DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}