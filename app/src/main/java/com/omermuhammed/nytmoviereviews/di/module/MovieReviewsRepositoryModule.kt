package com.omermuhammed.nytmoviereviews.di.module

import com.omermuhammed.nytmoviereviews.data.dao.MovieReviewsDao
import com.omermuhammed.nytmoviereviews.data.repository.MovieReviewsRepository
import com.omermuhammed.nytmoviereviews.network.ApiInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieReviewsRepositoryModule {

    @Provides
    @Singleton
    internal fun provideMovieReviewsRepository(movieReviewsDao: MovieReviewsDao, apiInterface: ApiInterface) : MovieReviewsRepository {
        return MovieReviewsRepository(movieReviewsDao, apiInterface)
    }
}