package com.example.premiummovieapp.data.model

import com.google.gson.annotations.SerializedName

data class MostPopularData(
    @SerializedName("items")
    val content: List<MostPopularDataDetail> = emptyList(),

    @SerializedName("errorMessage")
    val errorMessage: String = ""
)

data class MostPopularDataDetail(

    @SerializedName("id")
    val id: String = "0",

    @SerializedName("rank")
    val rank: String = "1",

    @SerializedName("rankUpDown")
    val rankUpDown: String = "+1",

    @SerializedName("title")
    val title: String = "title",

    @SerializedName("fullTitle")
    val fullTitle: String = "title",

    @SerializedName("year")
    val year: String = "2023",

    @SerializedName("image")
    val image: String = "image",

    @SerializedName("crew")
    val crew: String = "crew",

    @SerializedName("imDbRating")
    val imdbRating: String = "9.0",

    @SerializedName("imDbRatingCount")
    val imdbRatingCount: String = "100000"
)