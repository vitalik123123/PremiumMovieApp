package com.example.premiummovieapp.data.model.details

import com.google.gson.annotations.SerializedName

data class FilmDataDetails(
    @SerializedName("kinopoiskId")
    val id: Int = 1,

    @SerializedName("nameRu")
    val titleRu: String = "ТитлеРу",

    @SerializedName("nameOriginal")
    val titleOriginal: String = "TitleOriginal",

    @SerializedName("posterUrl")
    val poster: String = "url",

    @SerializedName("ratingKinopoisk")
    val ratingKinopoisk: Number? = 9.0,

    @SerializedName("ratingKinopoiskVoteCount")
    val countVoteKinopoisk: Int? = 100000,

    @SerializedName("ratingImdb")
    val ratingImdb: Number? = 9.0,

    @SerializedName("ratingImdbVoteCount")
    val countVoteImdb: Int? = 1000000,

    @SerializedName("year")
    val year: Int? = 2000,

    @SerializedName("filmLength")
    val length: Int? = 100,

    @SerializedName("description")
    val description: String = "description",

    @SerializedName("type")
    val type: String = "FILM",

    @SerializedName("countries")
    val countries: List<Country> = emptyList(),

    @SerializedName("genres")
    val genres: List<Genre> = emptyList(),

    @SerializedName("serial")
    val typeSerial: Boolean = false
)

data class Country(
    @SerializedName("country")
    val country: String = "USA"
)

data class Genre(
    @SerializedName("genre")
    val genre: String = "Comedy"
)
