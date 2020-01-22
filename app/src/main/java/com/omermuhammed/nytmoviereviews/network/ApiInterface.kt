package com.omermuhammed.nytmoviereviews.network

import com.omermuhammed.nytmoviereviews.utils.DVD_PICKS_ORDER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("reviews/dvd-picks.json")
    suspend fun getDvdPicks(
        @Query("order") order: String = DVD_PICKS_ORDER,
        @Query("offset") offset: Int
    ): Response<DvdPickResults>
}