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

    // leaving this code snippet here as it shows a clean way to add search in action bar
    // this will allow us to implement search feature with /search.json endpoint
    /*
    @GET("reviews/search.json")
    suspend fun getSearchResults (
        @Query("order") order: String = DVD_PICKS_ORDER,
        @Query("query") searchTerm: String,
        @Query("offset") offset: Int
    ): Response<DvdPickResults>
     */
}