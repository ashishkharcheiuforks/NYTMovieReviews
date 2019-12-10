package com.omermuhammed.nytmoviereviews.util

import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity
import com.omermuhammed.nytmoviereviews.network.Multimedia

object TestUtil {

    fun createMovieReviewEntity(display_title : String,
                          mpaa_rating : String,
                          headline : String,
                          summary_short : String,
                          byline : String,
                          multimedia: Multimedia,
                          publication_date : String) = MovieReviewsEntity(
        display_title = display_title,
        mpaa_rating = mpaa_rating,
        headline = headline,
        publication_date = publication_date,
        multimedia_src = multimedia.src,
        summary_short = summary_short,
        byline = byline
    )

    fun createMultimedia(type: String, src: String, w: Int, h: Int) =  Multimedia(
        type = type,
        src = src,
        width = w,
        height = h
    )
}
