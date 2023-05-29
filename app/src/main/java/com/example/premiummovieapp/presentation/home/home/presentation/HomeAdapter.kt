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
import com.example.premiummovieapp.data.model.MostPopularDataDetail
import com.example.premiummovieapp.databinding.HomeItemRecyclerBinding

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var moviesList: List<MostPopularDataDetail> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_moviesList: List<MostPopularDataDetail>) {
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

        fun bind(model: MostPopularDataDetail) = with(binding) {
            tvHomeItemRating.text = model.imdbRating
            Glide.with(itemView.context)
                .load(model.image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(imHomeItemPoster)

            itemView.setOnClickListener {
                listener?.onClick(model.id)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: String)
    }
}