package com.example.premiummovieapp.presentation.list.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import com.example.premiummovieapp.databinding.ListWatchlistItemRecyclerBinding

class ListWatchlistAdapter : RecyclerView.Adapter<ListWatchlistAdapter.ViewHolder>() {

    private var watchlistList: List<WatchlistEntity> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_watchlistList: List<WatchlistEntity>) {
        watchlistList = _watchlistList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListWatchlistItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = watchlistList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = watchlistList[position])
    }

    inner class ViewHolder(private val binding: ListWatchlistItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: WatchlistEntity) {
            binding.tvListItemWatchlistRating.text = model.rating.toString()
            binding.tvListItemWatchlistTitle.text = model.title

            Glide.with(itemView.context)
                .load(model.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(binding.imListItemWatchlistPoster)

            binding.imListItemWatchlistPoster.setOnClickListener {
                listener?.onClick(model.kinopoiskId)
            }

            binding.ivListItemWatchlistRemove.setOnClickListener {
                listener?.onClickRemove(model.kinopoiskId, model.title)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
        fun onClickRemove(modelId: Int, modelTitle: String)
    }

}