package com.omermuhammed.nytmoviereviews.network

import com.omermuhammed.nytmoviereviews.utils.API_KEY_QUERY_STRING
import com.omermuhammed.nytmoviereviews.utils.Keys
import com.omermuhammed.nytmoviereviews.utils.NYTIMES_URL
import com.omermuhammed.nytmoviereviews.utils.NoConnectionInterceptor
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory{

    private fun okhttpClient(): OkHttpClient {

        val apiKeyInterceptor = Interceptor { chain ->
            val url: HttpUrl = chain.request()
                    .url.newBuilder()
                    .addQueryParameter(API_KEY_QUERY_STRING, Keys.apiKey())
                    .build()

            val request: Request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

            chain.proceed(request)
        }

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(NoConnectionInterceptor())
        httpClient.addInterceptor(apiKeyInterceptor)
        return httpClient.build()
    }

    fun retrofit(): Retrofit {
        return Retrofit.Builder().client(okhttpClient())
                .baseUrl(NYTIMES_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }


}