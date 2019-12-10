package com.omermuhammed.nytmoviereviews.di.module

import android.app.Application
import androidx.room.Room
import com.omermuhammed.nytmoviereviews.data.MovieReviewsRoomDb
import com.omermuhammed.nytmoviereviews.data.dao.MovieReviewsDao
import com.omermuhammed.nytmoviereviews.network.ApiInterface
import com.omermuhammed.nytmoviereviews.utils.*
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

// Top level module for stuff used everywhere in app
@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): MovieReviewsRoomDb {
        return Room.databaseBuilder(app,
            MovieReviewsRoomDb::class.java, DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieReviewsDao(db: MovieReviewsRoomDb): MovieReviewsDao {
        return db.movieReviewsDao()
    }

    @Provides
    @Singleton
    internal fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(): OkHttpClient {

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

    @Provides
    @Singleton
    internal fun provideRetrofit(moshiConverterFactory: MoshiConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(NYTIMES_URL)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideMovieReviewsApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}