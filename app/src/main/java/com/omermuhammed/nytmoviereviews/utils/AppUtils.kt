package com.omermuhammed.nytmoviereviews.utils

class AppUtils {

    companion object {
        enum class MpaaRating(val ratingStr: String) {
            ALL("ALL"),
            G("G"),
            PG("PG"),
            PG13("PG-13"),
            R("R")
        }
    }

}