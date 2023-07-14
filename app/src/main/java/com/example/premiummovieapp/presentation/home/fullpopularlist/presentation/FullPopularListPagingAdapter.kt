package com.example.premiummovieapp.presentation.home.fullpopularlist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.model.FilmTopResponseFilmsForList
import com.example.premiummovieapp.databinding.FullPopularListItemRecyclerBinding

class FullPopularListPagingAdapter : PagingDataAdapter<FilmTopResponseFilmsForList, FullPopularListPagingAdapter.ViewHolder>(DataDiffer) {

    private var listener: OnItemClickListener? = null

    fun setListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.full_popular_list_item_recycler, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding by viewBinding(FullPopularListItemRecyclerBinding::bind)

        fun bind(model: FilmTopResponseFilmsForList?) =with(viewBinding) {
            if (model?.rating?.last() != '%') {
                tvRatingItemFullPopular.text = model?.rating
            } else {
                tvRatingItemFullPopular.text = ""
            }
            tvRankAndTitleItemFullPopular.text = model?.titleRu
            Glide.with(itemView.context)
                .load(model?.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(imPosterItemFullPopular)

            itemView.setOnClickListener {
                listener?.onClick(model!!.filmId)
            }

        }
    }

    object DataDiffer: DiffUtil.ItemCallback<FilmTopResponseFilmsForList>(){
        override fun areItemsTheSame(
            oldItem: FilmTopResponseFilmsForList,
            newItem: FilmTopResponseFilmsForList
        ): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(
            oldItem: FilmTopResponseFilmsForList,
            newItem: FilmTopResponseFilmsForList
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: Int)
    }
}