package com.example.premiummovieapp.data.repositories.local

import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity

interface MovieLocalDataSource {

    suspend fun getAllLocalWatchlist(): List<WatchlistEntity>

    suspend fun saveMovieToWatchlist(film: FilmDataDetails)

    suspend fun deleteMovieFromWatchlist(kinopoiskId: Int)

    suspend fun existsMovieToWatchlist(kinopoiskId: Int): Boolean

    suspend fun getAllLocalRatinglist(): List<RatingEntity>

    suspend fun saveMyRatingToRatinglist(film: FilmDataDetails, myRating: Int)

    suspend fun getMyRatingFromRatinglistToDetails(kinopoiskId: Int): RatingEntity

    suspend fun deleteMyRatingFromRatinglist(kinopoiskId: Int)

    suspend fun updateMyRatingFromRatinglist(kinopoiskId: Int, myRating: Int)

    suspend fun existsMyRatingToRatinglist(kinopoiskId: Int): Boolean
}