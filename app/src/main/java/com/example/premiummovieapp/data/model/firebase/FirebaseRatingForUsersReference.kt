package com.example.premiummovieapp.data.model.firebase

data class FirebaseRatingForUsersReference(
    val uid: String? = "",
    val kinopoiskId: Int? = 0,
    val userRating: Int? = 0,
    val timestamp: Long? = 0,
    val title: String? = "",
    val poster: String? = "",
    val year: Int? = 0,
    val kinopoiskRating: Double? = 0.0,
    val length: Int? = 0
)