package com.omermuhammed.nytmoviereviews.network

import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity
import com.squareup.moshi.Json

data class DvdPickResults(
    @Json(name = "results")
    val results: List<MovieReview>,
    @Json(name = "has_more")
    val has_more: Boolean
)

// Sometimes Multimedia can be empty
data class MovieReview(
    @Json(name = "display_title")
    val display_title: String,
    @Json(name = "mpaa_rating")
    val mpaa_rating: String,
    @Json(name = "byline")
    val byline: String,
    @Json(name = "headline")
    val headline: String,
    @Json(name = "summary_short")
    val summary_short: String,
    @Json(name = "publication_date")
    val publication_date: String,
    @Json(name = "multimedia")
    val multimedia: Multimedia? = null
)

// And sometimes the image URL would be empty
data class Multimedia(
    @Json(name = "type")
    val type: String,
    @Json(name = "src")
    val src: String? = null,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int
)

// Doing this to keep server schema separate from app DB schema, this requires
// less code changes if/when server DB schema changes.
fun MovieReview.toMovieReviewsEntity() = MovieReviewsEntity(
    display_title = display_title,
    mpaa_rating = mpaa_rating,
    byline = byline,
    headline = headline,
    summary_short = summary_short,
    publication_date = publication_date,
    multimedia_src = ""
)
