package com.omermuhammed.nytmoviereviews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity
import com.omermuhammed.nytmoviereviews.data.MovieReviewsRoomDb
import com.omermuhammed.nytmoviereviews.data.dao.MovieReviewsDao
import com.omermuhammed.nytmoviereviews.data.repository.MovieReviewsRepository
import com.omermuhammed.nytmoviereviews.network.ApiFactory
import com.omermuhammed.nytmoviereviews.network.Resource
import com.omermuhammed.nytmoviereviews.utils.AppUtils
import kotlinx.coroutines.launch

// Following MVVM architecture and ensuring a view model for every fragment
// Note that the view model only exposes LiveData
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var database: MovieReviewsRoomDb = MovieReviewsRoomDb.getDatabase(application)
    private var movieReviewsDao: MovieReviewsDao
    private val movieReviewsRepository: MovieReviewsRepository

    init {
        movieReviewsDao = database.movieReviewsDao()
        movieReviewsRepository = MovieReviewsRepository(movieReviewsDao, ApiFactory.nytimesApi)
    }

    private val movieReviewsList: MutableLiveData<Resource<List<MovieReviewsEntity>>> = movieReviewsRepository.dvdPickResults
    fun getMovieReviewsList() : LiveData<Resource<List<MovieReviewsEntity>>> = movieReviewsList

    private val moreResults: MutableLiveData<Boolean> = movieReviewsRepository.hasMoreResults
    fun hasMoreResults() : LiveData<Boolean> = moreResults

    fun fetchDvdPicksFromRepo(offset: Int, rating: AppUtils.Companion.MpaaRating) {
        viewModelScope.launch {
            movieReviewsRepository.fetchDvdPicks(offset, rating)
        }
    }
}