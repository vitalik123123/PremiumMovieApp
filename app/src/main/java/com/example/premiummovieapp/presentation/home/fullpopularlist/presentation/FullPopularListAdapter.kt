package com.example.premiummovieapp.presentation.home.fullpopularlist.presentation

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.model.MostPopularDataDetail
import com.example.premiummovieapp.databinding.FullPopularListItemRecyclerBinding

class FullPopularListAdapter() : RecyclerView.Adapter<FullPopularListAdapter.ViewHolder>() {

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
        val binding = FullPopularListItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = moviesList[position])
    }

    inner class ViewHolder(private val binding: FullPopularListItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MostPopularDataDetail) = with(binding) {
            val rankTitle = "${model.rank}.(${model.rankUpDown}) ${model.title}"
            val ss = SpannableString(rankTitle)
            val colorUpAndDown = upAndDownColor(model.rankUpDown)
            ss.setSpan(
                ForegroundColorSpan(colorUpAndDown),
                rankTitle.indexOf("(").plus(1),
                rankTitle.indexOf(")"),
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
            tvRatingItemFullPopular.text = model.imdbRating
            tvRankAndTitleItemFullPopular.text = ss
            Glide.with(itemView.context)
                .load(model.image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(imPosterItemFullPopular)

            itemView.setOnClickListener {
                listener?.onClick(model.id)
            }
        }

        private fun upAndDownColor(upDown: String): Int {
            val color: Int = if (upDown.first() == '+') {
                itemView.resources.getColor(R.color.up_color_for_full_list)
            } else if (upDown.first() == '-') {
                itemView.resources.getColor(R.color.down_color_for_full_list)
            } else {
                Color.GRAY
            }
            return color
        }
    }

    interface OnItemClickListener {
        fun onClick(modelId: String)
    }
}
