package com.omermuhammed.nytmoviereviews.ui

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.omermuhammed.nytmoviereviews.R
import com.omermuhammed.nytmoviereviews.adapter.MovieReviewsAdapter
import com.omermuhammed.nytmoviereviews.network.Resource
import com.omermuhammed.nytmoviereviews.utils.AppUtils.Companion.MpaaRating
import com.omermuhammed.nytmoviereviews.utils.INITIAL_OFFSET
import com.omermuhammed.nytmoviereviews.utils.OFFSET_INCREMENT
import com.omermuhammed.nytmoviereviews.utils.autoCleared
import com.omermuhammed.nytmoviereviews.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var recyclerView: RecyclerView

    private var movieReviewsAdapter: MovieReviewsAdapter by this.autoCleared()

    private lateinit var scrollListener: RecyclerView.OnScrollListener

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var fragmentContainer: ConstraintLayout

    private lateinit var placeholderText: AppCompatTextView

    private lateinit var progressBar: ProgressBar

    private val lastVisibleItemPosition: Int get() = linearLayoutManager.findLastVisibleItemPosition()

    private var currentRating: MpaaRating = MpaaRating.ALL

    private var offset: Int = INITIAL_OFFSET

    private var hasMoreMovieReviews: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root: View = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        recyclerView = root.findViewById(R.id.moviereviews_recyclerview)

        fragmentContainer = root.findViewById(R.id.fragment_home_layout)

        linearLayoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = linearLayoutManager

        movieReviewsAdapter = MovieReviewsAdapter(activity)

        recyclerView.adapter = movieReviewsAdapter

        setRecyclerViewScrollListener()

        placeholderText = root.findViewById(R.id.placeholder_text)

        progressBar = root.findViewById(R.id.spinner)

        setHasOptionsMenu(true)

        return root
    }

    // simple listener for endless scrolling
    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv, newState)

                val totalItemCount: Int? = rv.layoutManager?.itemCount

                if (totalItemCount == lastVisibleItemPosition + 1) {
                    if (hasMoreMovieReviews) {
                        getMovieReviewsData()
                    } else {
                        displayNoMoreResultsMessage()
                    }
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun displayNoMoreResultsMessage() {
        val noMoreResultsSnackbar: Snackbar = Snackbar.make(
            fragmentContainer,
            this.getString(
                R.string.no_more_results
            ),
            Snackbar.LENGTH_LONG
        )

        noMoreResultsSnackbar.show()
    }

    private fun initMovieReviewsList() {
        // use viewLifecycleOwner, instead of 'this'
        // In a fragment the view can be created more than once so you end up
        // making .observe call more than once, if the lifecycleOwner is the
        // fragment itself. But if you use viewLifecycleOwner the view is only
        // recreated after its been destroyed so the observer is
        // automatically cleaned up.
        homeViewModel.getMovieReviewsList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    placeholderText.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    placeholderText.visibility = View.GONE

                    if (!hasMoreMovieReviews) {
                        displayNoMoreResultsMessage()
                    } else {
                        movieReviewsAdapter.setMovieReviewsList(it.data)
                    }

                    recyclerView.visibility = View.VISIBLE
                }

                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    placeholderText.text = it.errorMessage
                    placeholderText.visibility = View.VISIBLE
                }
            }
        })

        homeViewModel.hasMoreResults().observe(viewLifecycleOwner, Observer {
            hasMoreMovieReviews = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_allratings_selected -> {
                currentRating = MpaaRating.ALL
                requestDvdPicks()
                true
            }
            R.id.action_g_selected -> {
                currentRating = MpaaRating.G
                requestDvdPicks()
                true
            }
            R.id.action_pg_selected -> {
                currentRating = MpaaRating.PG
                requestDvdPicks()
                true
            }
            R.id.action_pg13_selected -> {
                currentRating = MpaaRating.PG13
                requestDvdPicks()
                true
            }
            R.id.action_r_selected -> {
                currentRating = MpaaRating.R
                requestDvdPicks()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun requestDvdPicks() {
        offset = INITIAL_OFFSET
        hasMoreMovieReviews = true

        getMovieReviewsData()

        placeholderText.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        initMovieReviewsList()
    }

    private fun getMovieReviewsData() {
        homeViewModel.fetchDvdPicksFromRepo(offset, currentRating)
        offset += OFFSET_INCREMENT
    }
}