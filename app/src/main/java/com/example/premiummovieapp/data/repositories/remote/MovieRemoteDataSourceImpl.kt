package com.example.premiummovieapp.data.repositories.remote

import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.data.model.FilmResponseSearchByKeyword
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForRatingReference
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForUsersReference
import com.example.premiummovieapp.presentation.main.SplashFragment
import com.example.premiummovieapp.data.model.firebase.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val movieApi: MovieApi,
    private val firebaseDatabase: FirebaseDatabase
) : MovieRemoteDataSource {

    override suspend fun getTopFilms(type: String, page: Int) =
        movieApi.getTopFilms(type = type, page = page)

    override suspend fun getFilmDataDetails(id: Int): Response<FilmDataDetails> =
        movieApi.getFilmDataDetails(id = id)

    override suspend fun getFilmCast(filmId: Int): Response<List<FilmCast>> =
        movieApi.getFilmCast(filmId = filmId)

    override suspend fun getFilmSequelsAndPrequels(id: Int): Response<List<FilmSequelsAndPrequels>> =
        movieApi.getFilmSequelsAndPrequels(id = id)

    override suspend fun getFilmSimilars(id: Int): Response<FilmSimilarsResponseData> =
        movieApi.getFilmSimilars(id = id)

    override suspend fun getFilmsSearchByKeyword(
        keyword: String,
        page: Int
    ): Response<FilmResponseSearchByKeyword> =
        movieApi.getFilmsSearchByKeyword(keyword = keyword, page = page)

    override suspend fun saveUserDataToRealtimeDatabaseFirebase(
        currentUserUid: String,
        user: FirebaseUser
    ) {
        val reference = firebaseDatabase.getReference(SplashFragment.FIREBASE_REFERENCE_USERS)
        reference.child(currentUserUid).child(SplashFragment.FIREBASE_CHILD_USER_DATA)
            .setValue(user).await()
    }

    override suspend fun saveUserRatingToRealtimeDatabaseFirebase(
        currentUserUid: String,
        kinopoiskId: Int,
        ratingForRatingReference: FirebaseRatingForRatingReference,
        ratingForUsersReference: FirebaseRatingForUsersReference
    ) {
        val usersReference = firebaseDatabase.getReference(SplashFragment.FIREBASE_REFERENCE_USERS)
        usersReference.child(currentUserUid).child(SplashFragment.FIREBASE_CHILD_USER_RATING)
            .child(kinopoiskId.toString()).setValue(ratingForUsersReference).await()

        val ratingReference =
            firebaseDatabase.getReference(SplashFragment.FIREBASE_REFERENCE_RATINGS)
        ratingReference.child(kinopoiskId.toString()).child(currentUserUid)
            .setValue(ratingForRatingReference).await()
    }

    override suspend fun getUserRatingFromRealtimeDatabaseFirebase(
        currentUserUid: String,
        kinopoiskId: Int
    ): FirebaseRatingForRatingReference? {
        var rating: FirebaseRatingForRatingReference? = null
        val reference = firebaseDatabase.getReference(SplashFragment.FIREBASE_REFERENCE_USERS)
        reference.child(currentUserUid).child(SplashFragment.FIREBASE_CHILD_USER_RATING)
            .child(kinopoiskId.toString()).get().addOnSuccessListener {
                rating = it.getValue(FirebaseRatingForRatingReference::class.java)
            }.await()

        return rating
    }

    override suspend fun getListUserRatingFromRealtimeDatabaseFirebase(currentUserUid: String): ArrayList<FirebaseRatingForUsersReference> {
        val list: ArrayList<FirebaseRatingForUsersReference> = arrayListOf()
        val reference = firebaseDatabase.getReference(SplashFragment.FIREBASE_REFERENCE_USERS)
        reference.child(currentUserUid).child(SplashFragment.FIREBASE_CHILD_USER_RATING).get()
            .addOnSuccessListener { snapshot ->
                snapshot.children.forEach {
                    list.add(it.getValue(FirebaseRatingForUsersReference::class.java)!!)
                }
            }.await()

        return list
    }

    override suspend fun deleteUserRatingToRealtimeDatabaseFirebase(
        currentUserUid: String,
        kinopoiskId: Int
    ) {
        val usersReference = firebaseDatabase.getReference(SplashFragment.FIREBASE_REFERENCE_USERS)
        usersReference.child(currentUserUid).child(SplashFragment.FIREBASE_CHILD_USER_RATING)
            .child(kinopoiskId.toString()).removeValue().await()

        val ratingReference =
            firebaseDatabase.getReference(SplashFragment.FIREBASE_REFERENCE_RATINGS)
        ratingReference.child(kinopoiskId.toString()).child(currentUserUid)
            .removeValue().await()
    }
}