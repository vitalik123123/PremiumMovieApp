package com.example.premiummovieapp.data.model.details

import com.google.gson.annotations.SerializedName

data class FilmCast(
    @SerializedName("staffId")
    val staffId: Int = 1,

    @SerializedName("nameRu")
    val nameRu: String = "Крис Пратт",

    @SerializedName("description")
    val descriptionRole: String? = "Peter Quill",

    @SerializedName("posterUrl")
    val posterCast: String = "url",

    @SerializedName("professionKey")
    val professionKey: ProfessionKey
)

enum class ProfessionKey {
    WRITER, OPERATOR, EDITOR, COMPOSER, PRODUCER_USSR, TRANSLATOR, DIRECTOR, DESIGN, PRODUCER, ACTOR, VOICE_DIRECTOR, UNKNOWN
}
