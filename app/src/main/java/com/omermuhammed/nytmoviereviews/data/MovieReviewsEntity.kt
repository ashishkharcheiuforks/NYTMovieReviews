package com.omermuhammed.nytmoviereviews.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Using columinfo to make it easy to rename incase the schema changes
@Entity(tableName = "movie_reviews")
data class MovieReviewsEntity(
    @PrimaryKey @ColumnInfo(name = "display_title") var display_title: String,
    @ColumnInfo(name = "mpaa_rating") var mpaa_rating: String = "",
    @ColumnInfo(name = "byline") var byline: String = "",
    @ColumnInfo(name = "headline") var headline: String = "",
    @ColumnInfo(name = "summary_short") var summary_short: String = "",
    @ColumnInfo(name = "publication_date") var publication_date: String = "",
    @ColumnInfo(name = "multimedia") var multimedia_src: String? = ""
)
