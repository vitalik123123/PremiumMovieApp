package com.example.premiummovieapp.data.model.firebase

data class FirebaseUser(
    val uid: String? = "",
    val username: String? = "",
    val email: String? = "",
    val timestamp: Long? = 0
)