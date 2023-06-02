package com.example.premiummovieapp.presentation.details.moviedetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.data.model.MoreLikeThisList
import com.example.premiummovieapp.databinding.HomeItemRecyclerBinding

class MovieDetailsMoreLikeThisAdapter() :
    RecyclerView.Adapter<MovieDetailsMoreLikeThisAdapter.ViewHolder>() {

    private var moreLikeThisList: List<MoreLikeThisList> = emptyList()
    private var listener: OnItemClickListener? = null

    fun setData(_moreLikeThisList: List<MoreLikeThisList>) {
        moreLikeThisList = _moreLikeThisList
        notifyDataSetChanged()
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = moreLikeThisList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moreLikeThisList[position])
    }

    inner class ViewHolder(private val binding: HomeItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MoreLikeThisList) = with(binding) {
            tvHomeItemRating.text = model.moreLikeThisImdbRating
            Glide.with(itemView.context)
                .load(model.moreLikeThisImage)
                .transform(CenterCrop(), RoundedCorners(16))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imHomeItemPoster)

            itemView.setOnClickListener {
                listener?.onClick(model.moreLikeThisId)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: String)
    }
}