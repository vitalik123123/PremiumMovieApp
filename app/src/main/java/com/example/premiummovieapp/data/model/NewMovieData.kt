package com.example.premiummovieapp.data.model

import com.google.gson.annotations.SerializedName

data class NewMovieData(
    @SerializedName("items")
    val content: List<NewMovieDataDetail> = emptyList(),

    @SerializedName("errorMessage")
    val errorMessage: String = ""
)

data class NewMovieDataDetail(

    @SerializedName("id")
    val id: String = "0",

    @SerializedName("title")
    val title: String = "title",

    @SerializedName("releaseState")
    val release: String = "Jan 1, 2000",

    @SerializedName("image")
    val image: String = "image",

    @SerializedName("genres")
    val genres: String = "genres"
)
