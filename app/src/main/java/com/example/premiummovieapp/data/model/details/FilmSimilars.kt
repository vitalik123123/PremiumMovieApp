package com.example.premiummovieapp.data.model.details

import com.google.gson.annotations.SerializedName

data class FilmSimilarsResponseData(
    @SerializedName("total")
    val total: Int = 1,

    @SerializedName("items")
    val filmSimilarsList: List<FilmSimilars> = emptyList()
)

data class FilmSimilars(
    @SerializedName("filmId")
    val filmId: Int = 1,

    @SerializedName("nameRu")
    val titleRu: String = "ТитлеРу",

    @SerializedName("posterUrl")
    val poster: String = "url"
)
