package com.omermuhammed.nytmoviereviews.data.repository

import androidx.lifecycle.MutableLiveData
import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity
import com.omermuhammed.nytmoviereviews.data.dao.MovieReviewsDao
import com.omermuhammed.nytmoviereviews.network.*
import com.omermuhammed.nytmoviereviews.utils.AppUtils.Companion.MpaaRating
import retrofit2.Response

class MovieReviewsRepository(
    private val movieReviewsDao: MovieReviewsDao,
    private val apiClient: ApiInterface
) {

    val dvdPickResults: MutableLiveData<Resource<List<MovieReviewsEntity>>> = MutableLiveData()
    val hasMoreResults: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun fetchDvdPicks(currentOffset: Int, mpaaRating: MpaaRating) {
        var searchResult: Resource<List<MovieReviewsEntity>>

        searchResult = Resource.Loading()

        dvdPickResults.postValue(searchResult)

        try {
            val response: Response<DvdPickResults> = apiClient.getDvdPicks(offset = currentOffset)

            if (response.isSuccessful) {
                val dvdPickResults: DvdPickResults = response.body()!!

                if (dvdPickResults.has_more && dvdPickResults.results.isNotEmpty()) {

                    val movieReviewsList: List<MovieReview> = dvdPickResults.results

                    var movieReview: MovieReviewsEntity

                    movieReviewsList.forEach {
                        movieReview = it.toMovieReviewsEntity()

                        movieReview.multimedia_src = it.multimedia?.src
                        movieReviewsDao.insertMovieReview(movieReview)
                    }

                    if (mpaaRating == MpaaRating.ALL) {
                        //Timber.tag(TIMBER_TAG).e("mpaa rating selected is: ALL/ANY")
                        searchResult = Resource.Success(movieReviewsDao.getAllMovieReviews())
                    } else {
                        //Timber.tag(TIMBER_TAG).e("mpaa rating selected is: %s", mpaaRating.ratingStr)
                        searchResult = Resource.Success(movieReviewsDao.getMovieReviewsByRating(rating = mpaaRating.ratingStr))
                    }

                    hasMoreResults.postValue(dvdPickResults.has_more)
                    this.dvdPickResults.postValue(searchResult)
                } else {
                    hasMoreResults.postValue(dvdPickResults.has_more)
                    searchResult = Resource.Success(emptyList())
                    this.dvdPickResults.postValue(searchResult)
                }

            } else {
                searchResult = Resource.Failure(
                    "Response code:" +
                            response.code() +
                            ", error message: " +
                            response.message())

                dvdPickResults.postValue(searchResult)
            }
        } catch (e: Throwable) {
            searchResult = Resource.Failure(e.message!!)

            dvdPickResults.postValue(searchResult)
        }
    }
}
