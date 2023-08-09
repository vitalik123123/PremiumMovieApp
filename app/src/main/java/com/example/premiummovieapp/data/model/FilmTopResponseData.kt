package com.example.premiummovieapp.data.model

import com.google.gson.annotations.SerializedName

data class FilmTopResponseData(

    @SerializedName("pagesCount")
    val pages: Int = 35,

    @SerializedName("films")
    val films: List<FilmTopResponseFilmsForList> = emptyList()
)

data class FilmTopResponseFilmsForList(

    @SerializedName("filmId")
    val filmId: Int = 1,

    @SerializedName("nameRu")
    val titleRu: String = "ТитлеРу",

    @SerializedName("rating")
    val rating: String? = "9.0",

    @SerializedName("posterUrl")
    val poster: String = "url"
)