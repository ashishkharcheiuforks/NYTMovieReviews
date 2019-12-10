package com.omermuhammed.nytmoviereviews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity
import com.omermuhammed.nytmoviereviews.data.repository.MovieReviewsRepository
import com.omermuhammed.nytmoviereviews.network.Resource
import com.omermuhammed.nytmoviereviews.utils.AppUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

// Following MVVM architecture and ensuring a view model for every fragment
// Note that the view model only exposes LiveData

// Use @AssistedInject to pass in variable (https://proandroiddev.com/dagger-assisted-injection-2002885b3cba)
class HomeViewModel @Inject constructor(
    val movieReviewsRepository: MovieReviewsRepository
) : ViewModel() {

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