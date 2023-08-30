package com.example.premiummovieapp.data.repositories.remote

import com.example.premiummovieapp.data.model.FilmResponseSearchByKeyword
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.FilmTopResponseData
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForRatingReference
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForUsersReference
import com.example.premiummovieapp.data.model.firebase.FirebaseUser
import retrofit2.Response

interface MovieRemoteDataSource {

    suspend fun getTopFilms(type: String, page: Int): Response<FilmTopResponseData>

    suspend fun getFilmDataDetails(id: Int): Response<FilmDataDetails>

    suspend fun getFilmCast(filmId: Int): Response<List<FilmCast>>

    suspend fun getFilmSequelsAndPrequels(id: Int): Response<List<FilmSequelsAndPrequels>>

    suspend fun getFilmSimilars(id: Int): Response<FilmSimilarsResponseData>

    suspend fun getFilmsSearchByKeyword(
        keyword: String,
        page: Int
    ): Response<FilmResponseSearchByKeyword>

    suspend fun saveUserDataToRealtimeDatabaseFirebase(currentUserUid: String, user: FirebaseUser)

    suspend fun saveUserRatingToRealtimeDatabaseFirebase(
        currentUserUid: String,
        kinopoiskId: Int,
        ratingForRatingReference: FirebaseRatingForRatingReference,
        ratingForUsersReference: FirebaseRatingForUsersReference
    )

    suspend fun getUserRatingFromRealtimeDatabaseFirebase(
        currentUserUid: String,
        kinopoiskId: Int
    ): FirebaseRatingForRatingReference?

    suspend fun getListUserRatingFromRealtimeDatabaseFirebase(currentUserUid: String): ArrayList<FirebaseRatingForUsersReference>

    suspend fun deleteUserRatingToRealtimeDatabaseFirebase(
        currentUserUid: String,
        kinopoiskId: Int
    )
}