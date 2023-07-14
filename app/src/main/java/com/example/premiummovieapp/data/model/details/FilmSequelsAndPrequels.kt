package com.example.premiummovieapp.data.model.details

import com.google.gson.annotations.SerializedName

data class FilmSequelsAndPrequels(
    @SerializedName("filmId")
    val filmId: Int = 1,

    @SerializedName("nameRu")
    val nameRU: String = "Джон Уик",

    @SerializedName("posterUrl")
    val poster: String = "url"
)
