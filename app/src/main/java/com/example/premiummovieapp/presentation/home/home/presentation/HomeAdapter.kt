package com.example.premiummovieapp.presentation.home.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.model.FilmTopResponseFilmsForList
import com.example.premiummovieapp.databinding.HomeItemRecyclerBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var moviesList: List<FilmTopResponseFilmsForList> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_moviesList: List<FilmTopResponseFilmsForList>) {
        moviesList = _moviesList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HomeItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = moviesList[position])
    }

    inner class ViewHolder(private val binding: HomeItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: FilmTopResponseFilmsForList) = with(binding) {
            if (model.rating?.last() != '%') {
                tvHomeItemRating.text = model.rating
            } else {
                tvHomeItemRating.text = ""
            }
            Glide.with(itemView.context)
                .load(model.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(imHomeItemPoster)

            itemView.setOnClickListener {
                listener?.onClick(model.filmId)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
    }
}