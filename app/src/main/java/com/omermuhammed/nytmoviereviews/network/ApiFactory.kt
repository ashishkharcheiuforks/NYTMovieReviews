package com.omermuhammed.nytmoviereviews.network

object ApiFactory {
    val nytimesApi: ApiInterface = RetrofitFactory.retrofit().create(ApiInterface::class.java)
}