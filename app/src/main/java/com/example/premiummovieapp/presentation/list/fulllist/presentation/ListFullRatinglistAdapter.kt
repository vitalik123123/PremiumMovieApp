package com.example.premiummovieapp.presentation.list.fulllist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import com.example.premiummovieapp.databinding.ListFullistWatchlistItemRecyclerBinding
import com.example.premiummovieapp.databinding.ListFulllistRatingItemRecyclerBinding

class ListFullRatinglistAdapter : RecyclerView.Adapter<ListFullRatinglistAdapter.ViewHolder>() {

    private var fullRatinglistList: List<RatingEntity> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_fullRatinglistList: List<RatingEntity>) {
        fullRatinglistList = _fullRatinglistList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFulllistRatingItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = fullRatinglistList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = fullRatinglistList[position])
    }

    inner class ViewHolder(private val binding: ListFulllistRatingItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RatingEntity) {
            binding.tvListItemFullListRatinglistRating.text = model.rating.toString()
            binding.tvListItemFullListRatinglistTitle.text = model.title
            binding.tvListItemFullListRatinglistYear.text = model.year.toString()
            binding.tvListItemFullListRatinglistLength.text = model.length.toString().plus("мин")
            binding.tvListItemFullListRatinglistMyRating.text = model.myRating.toString()

            Glide.with(itemView.context)
                .load(model.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(binding.ivListItemFullListRatinglistPoster)

            itemView.setOnClickListener {
                listener?.onClick(modelId = model.kinopoiskId)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
    }
}