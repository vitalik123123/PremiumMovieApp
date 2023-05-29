package com.example.premiummovieapp.data.model

import com.google.gson.annotations.SerializedName

data class SeasonEpisodesData(

    @SerializedName("imDbId")
    val id: String = "0",

    @SerializedName("title")
    val title: String = "title",

    @SerializedName("episodes")
    val episodesInfo: List<SeasonEpisodesInfo> = emptyList()
)

data class SeasonEpisodesInfo(

    @SerializedName("id")
    val infoId: String = "0",

    @SerializedName("seasonNumber")
    val infoSeasonNumber: String = "1",

    @SerializedName("episodeNumber")
    val infoEpisodeNumber: String = "1",

    @SerializedName("title")
    val infoTitle: String = "title",

    @SerializedName("image")
    val infoImage: String = "image",

    @SerializedName("released")
    val infoReleased: String = "Jan 1, 2000",

    @SerializedName("imDbRating")
    val infoImdbRating: String = "9.0"
)
