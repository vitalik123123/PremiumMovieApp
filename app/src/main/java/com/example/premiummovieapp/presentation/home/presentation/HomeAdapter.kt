package com.example.premiummovieapp.presentation.home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.model.MostPopularDataDetail

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
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item_recycler, parent, false)
        )
    }

    override fun getItemCount(): Int = moviesList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = moviesList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemPoster: ImageView = itemView.findViewById(R.id.imHomeItemPoster)
        private var itemImdbRating: TextView = itemView.findViewById(R.id.tvHomeItemRating)

        fun bind(model: MostPopularDataDetail) {
            itemImdbRating.text = model.imdbRating
            Glide.with(itemView.context)
                .load(model.image)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(itemPoster)
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: String)
    }
}