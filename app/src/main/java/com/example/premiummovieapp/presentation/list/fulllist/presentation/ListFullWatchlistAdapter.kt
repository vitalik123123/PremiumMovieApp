package com.example.premiummovieapp.presentation.list.fulllist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import com.example.premiummovieapp.databinding.ListFullistWatchlistItemRecyclerBinding

class ListFullWatchlistAdapter : RecyclerView.Adapter<ListFullWatchlistAdapter.ViewHolder>() {

    private var fullWatchlistList: List<WatchlistEntity> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_fullWatchlistList: List<WatchlistEntity>) {
        fullWatchlistList = _fullWatchlistList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFullistWatchlistItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = fullWatchlistList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = fullWatchlistList[position])
    }

    inner class ViewHolder(private val binding: ListFullistWatchlistItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: WatchlistEntity) {
            binding.tvListItemFullListWatchlistRating.text = model.rating.toString()
            binding.tvListItemFullListWatchlistTitle.text = model.title
            binding.tvListItemFullListWatchlistYear.text = model.year.toString()
            binding.tvListItemFullListWatchlistLength.text = model.length.toString().plus("мин")

            Glide.with(itemView.context)
                .load(model.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(binding.ivListItemFullListWatchlistPoster)

            itemView.setOnClickListener {
                listener?.onClick(modelId = model.kinopoiskId)
            }

            binding.ivListItemFullListWatchlistRemove.setOnClickListener {
                listener?.onClickRemove(modelId = model.kinopoiskId, modelTitle = model.title)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
        fun onClickRemove(modelId: Int, modelTitle: String)
    }
}