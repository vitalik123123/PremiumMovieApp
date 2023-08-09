package com.example.premiummovieapp.presentation.list.fulllist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import com.example.premiummovieapp.databinding.ListFullistItemRecyclerBinding

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
        val binding = ListFullistItemRecyclerBinding.inflate(
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

    inner class ViewHolder(private val binding: ListFullistItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: WatchlistEntity) {
            binding.tvListItemFullListRating.text = model.rating.toString()
            binding.tvListItemFullListTitle.text = model.title
            binding.tvListItemFullListYear.text = model.year.toString()
            binding.tvListItemFullListLength.text = "${model.length.toString()}мин"

            Glide.with(itemView.context)
                .load(model.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(binding.ivListItemFullListPoster)

            itemView.setOnClickListener {
                listener?.onClick(modelId = model.kinopoiskId)
            }

            binding.ivListItemFullListRemove.setOnClickListener {
                listener?.onClickRemove(modelId = model.kinopoiskId, modelTitle = model.title)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
        fun onClickRemove(modelId: Int, modelTitle: String)
    }
}