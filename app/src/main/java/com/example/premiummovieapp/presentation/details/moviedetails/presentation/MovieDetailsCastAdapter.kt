package com.example.premiummovieapp.presentation.details.moviedetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.databinding.MoviesDetailsCastItemRecyclerBinding

class MovieDetailsCastAdapter() : RecyclerView.Adapter<MovieDetailsCastAdapter.ViewHolder>() {

    private var castList: List<FilmCast> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_castList: List<FilmCast>) {
        castList = _castList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MoviesDetailsCastItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = castList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = castList[position])
    }

    inner class ViewHolder(private val binding: MoviesDetailsCastItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: FilmCast) = with(binding) {
            tvMovieDetailsCastItemName.text = model.nameRu
            tvMovieDetailsCastItemCharacter.text = model.descriptionRole
            Glide.with(itemView.context)
                .load(model.posterCast)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .circleCrop()
                .into(ivMovieDetailsCastItemImage)
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: String)
    }
}