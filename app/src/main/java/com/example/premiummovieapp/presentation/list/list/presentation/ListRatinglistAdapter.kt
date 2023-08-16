package com.example.premiummovieapp.presentation.list.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity
import com.example.premiummovieapp.databinding.ListRatinglistItemRecyclerBinding

class ListRatinglistAdapter : RecyclerView.Adapter<ListRatinglistAdapter.ViewHolder>() {

    private var ratinglistList: List<RatingEntity> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_ratinglistList: List<RatingEntity>) {
        ratinglistList = _ratinglistList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRatinglistItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = ratinglistList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = ratinglistList[position])
    }

    inner class ViewHolder(private val binding: ListRatinglistItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RatingEntity) {
            binding.tvListItemRatinglistRating.text = model.rating.toString()
            binding.tvListItemRatinglistTitle.text = model.title
            binding.tvListItemRatinglistMyRating.text = model.myRating.toString()

            Glide.with(itemView.context)
                .load(model.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(binding.imListItemRatinglistPoster)

            binding.imListItemRatinglistPoster.setOnClickListener {
                listener?.onClick(model.kinopoiskId)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
    }

}