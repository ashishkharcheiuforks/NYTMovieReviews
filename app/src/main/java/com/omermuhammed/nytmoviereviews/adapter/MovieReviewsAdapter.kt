package com.omermuhammed.nytmoviereviews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omermuhammed.nytmoviereviews.R
import com.omermuhammed.nytmoviereviews.data.MovieReviewsEntity

class MovieReviewsAdapter internal constructor(context: FragmentActivity?) :
    ListAdapter<MovieReviewsEntity, MovieReviewsAdapter.MovieReviewsViewHolder>(
        MovieReviewsDiffCallback()
    ) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var movieReviewsList: List<MovieReviewsEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewsViewHolder {
        val itemView = inflater.inflate(R.layout.moviereviews_view_item, parent, false)
        return MovieReviewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieReviewsViewHolder, position: Int) {
        if (movieReviewsList.isEmpty()) {
            holder.thumbnail.setImageResource(R.drawable.baseline_insert_photo_24)
            holder.displayTitle.setText(R.string.empty_photo)
        } else {
            val movieReviewsItem: MovieReviewsEntity = movieReviewsList[position]

            holder.displayTitle.text = movieReviewsItem.display_title

            holder.byline.text = holder.itemView.context.getString(
                R.string.byline, movieReviewsItem.byline
            )

            holder.mpaaRating.text = holder.itemView.context.getString(
                R.string.mpaa_rating,
                movieReviewsItem.mpaa_rating
            )

            holder.publicationDate.text = holder.itemView.context.getString(
                R.string.publication_date,
                movieReviewsItem.publication_date
            )

            val url: String? = movieReviewsItem.multimedia_src

            // sometimes the server endpoint doesn't have multimedia data, sucks but what can we do.
            if (url.isNullOrEmpty()) {
                holder.thumbnail.setImageResource(R.drawable.baseline_insert_photo_24)
            } else {
                Glide.with(holder.itemView.context)
                    .load(movieReviewsItem.multimedia_src)
                    .centerInside()
                    .into(holder.thumbnail)
            }
        }
    }

    override fun getItemCount() = movieReviewsList.size

    internal fun setMovieReviewsList(movieReviews: List<MovieReviewsEntity>) {
        this.movieReviewsList = movieReviews
        notifyDataSetChanged()
    }

    class MovieReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        var displayTitle: TextView = itemView.findViewById(R.id.displayTitle)
        var byline: TextView = itemView.findViewById(R.id.byline)
        var mpaaRating: TextView = itemView.findViewById(R.id.mpaaRating)
        var publicationDate: TextView = itemView.findViewById(R.id.publicationDate)
    }
}

// Forced to used display_title to avoid dups as there is no ID coming in from server
class MovieReviewsDiffCallback : DiffUtil.ItemCallback<MovieReviewsEntity>() {

    override fun areItemsTheSame(
        oldItem: MovieReviewsEntity,
        newItem: MovieReviewsEntity
    ): Boolean {
        return oldItem.display_title == newItem.display_title
    }

    override fun areContentsTheSame(
        oldItem: MovieReviewsEntity,
        newItem: MovieReviewsEntity
    ): Boolean {
        return oldItem == newItem
    }


}