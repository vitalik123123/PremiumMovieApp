package com.example.premiummovieapp.presentation.details.moviedetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.model.SeasonEpisodesInfo
import com.example.premiummovieapp.databinding.MoviesDetailsEpisodesItemRecyclerBinding

class MovieDetailsEpisodesAdapter() :
    RecyclerView.Adapter<MovieDetailsEpisodesAdapter.ViewHolder>() {

    private var episodesList: List<SeasonEpisodesInfo> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_episodesList: List<SeasonEpisodesInfo>) {
        episodesList = _episodesList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MoviesDetailsEpisodesItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = episodesList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = episodesList[position])
    }

    inner class ViewHolder(private val binding: MoviesDetailsEpisodesItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: SeasonEpisodesInfo) = with(binding) {
            tvMovieDetailsEpisodesItemTitle.text = model.infoTitle
            tvMovieDetailsEpisodesItemRelease.text = model.infoReleased
            tvMovieDetailsEpisodesItemRating.text = model.infoImdbRating
            tvMovieDetailsEpisodesItemNumber.text =
                "s.${model.infoSeasonNumber} e.${model.infoEpisodeNumber}"
            Glide.with(itemView.context)
                .load(model.infoImage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(ivMovieDetailsEpisodesItemImage)
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: String)
    }
}