package com.example.premiummovieapp.data.model

import com.google.gson.annotations.SerializedName

data class FilmResponseSearchByKeyword(
    @SerializedName("keyword")
    val keyword: String = "keyword",

    @SerializedName("pageCount")
    val pageCount: Int = 5,

    @SerializedName("films")
    val listFilms: List<FilmsListSearchByKeyword> = emptyList()
)

data class FilmsListSearchByKeyword(
    @SerializedName("filmId")
    val filmId: Int = 1,

    @SerializedName("nameRu")
    val titleRu: String = "ТитлеРу",

    @SerializedName("nameEn")
    val titleEn: String = "TitleEn",

    @SerializedName("rating")
    val rating: String = "9.0",

    @SerializedName("posterUrl")
    val poster: String = "url"
)
