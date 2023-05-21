package com.example.premiummovieapp.presentation.home.fullpopularlist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.model.NewMovieDataDetail
import com.example.premiummovieapp.databinding.ComingSoonItemRecyclerBinding

class ComingSoonListAdapter(): RecyclerView.Adapter<ComingSoonListAdapter.ViewHolder>() {

    private var moviesList: List<NewMovieDataDetail> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_moviesList: List<NewMovieDataDetail>){
        moviesList = _moviesList
        notifyDataSetChanged()
    }

    fun setOnCLickListener(_listener: OnItemClickListener){
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ComingSoonItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = moviesList[position])
    }

    inner class ViewHolder(private val binding: ComingSoonItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NewMovieDataDetail) = with(binding) {
            tvTitleItemComingSoon.text = model.title
            tvGenresItemComingSoon.text = model.genres
            tvReleaseItemComingSoon.text = model.release
            Glide.with(itemView.context)
                .load(model.image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(ivPosterItemComingSoon)
        }
    }

    interface OnItemClickListener{
        fun onClick(modelId: String)
    }
}