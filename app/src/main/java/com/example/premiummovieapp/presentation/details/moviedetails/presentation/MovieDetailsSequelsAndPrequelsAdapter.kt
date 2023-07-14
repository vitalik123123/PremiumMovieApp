package com.example.premiummovieapp.presentation.details.moviedetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.databinding.MoviesDetailsSequelsAndPrequelsItemRecyclerBinding

class MovieDetailsSequelsAndPrequelsAdapter() :
    RecyclerView.Adapter<MovieDetailsSequelsAndPrequelsAdapter.ViewHolder>() {

    private var episodesList: List<FilmSequelsAndPrequels> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_episodesList: List<FilmSequelsAndPrequels>) {
        episodesList = _episodesList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MoviesDetailsSequelsAndPrequelsItemRecyclerBinding.inflate(
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

    inner class ViewHolder(private val binding: MoviesDetailsSequelsAndPrequelsItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: FilmSequelsAndPrequels) = with(binding) {
            tvMovieDetailsSequelsAndPrequelsTitleText.text = model.nameRU
            Glide.with(itemView.context)
                .load(model.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(ivMovieDetailsSequelsAndPrequelsItemImage)

            itemView.setOnClickListener {
                listener?.onClick(model.filmId)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
    }
}