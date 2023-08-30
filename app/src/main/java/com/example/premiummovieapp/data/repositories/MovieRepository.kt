package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.FilmResponseSearchByKeyword
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.FilmTopResponseData
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForRatingReference
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForUsersReference
import com.example.premiummovieapp.data.model.firebase.FirebaseUser

interface MovieRepository {

    suspend fun getTopFilms(type: String, page: Int): FilmTopResponseData?

    suspend fun getFilmDataDetails(id: Int): FilmDataDetails?

    suspend fun getFilmCast(filmId: Int): List<FilmCast>?

    suspend fun getFilmSequelsAndPrequels(id: Int): List<FilmSequelsAndPrequels>?

    suspend fun getFilmSimilars(id: Int): FilmSimilarsResponseData?

    suspend fun getFilmsSearchByKeyword(keyword: String, page: Int): FilmResponseSearchByKeyword?

    suspend fun getAllLocalWatchlist(): List<WatchlistEntity>

    suspend fun saveMovieToWatchList(film: FilmDataDetails)

    suspend fun deleteMovieFromWatchlist(kinopoiskId: Int)

    suspend fun existsMovieToWatchList(kinopoiskId: Int): Boolean

    suspend fun getAllLocalRatinglist(): List<RatingEntity>

    suspend fun saveMyRatingToRatinglist(film: FilmDataDetails, myRating: Int)

    suspend fun getMyRatingFromRatinglistToDetails(kinopoiskId: Int): Int

    suspend fun deleteMyRatingFromRatinglist(kinopoiskId: Int)

    suspend fun updateMyRatingFromRatinglist(kinopoiskId: Int, myRating: Int)

    suspend fun existsMyRatingToRatinglist(kinopoiskId: Int): Boolean

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