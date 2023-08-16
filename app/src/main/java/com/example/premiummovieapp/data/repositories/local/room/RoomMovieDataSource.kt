package com.example.premiummovieapp.data.repositories.local.room

import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.repositories.local.MovieLocalDataSource
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingDao
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistDao
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity

class RoomMovieDataSource(
    private val watchlistDao: WatchlistDao,
    private val ratingDao: RatingDao
) : MovieLocalDataSource {

    override suspend fun getAllLocalWatchlist(): List<WatchlistEntity> =
        watchlistDao.getAllLocalWatchlist()

    override suspend fun saveMovieToWatchlist(film: FilmDataDetails) =
        watchlistDao.saveMovieToWatchlist(film = WatchlistEntity.fromFilmDataDetails(film = film))

    override suspend fun deleteMovieFromWatchlist(kinopoiskId: Int) =
        watchlistDao.deleteMovieFromWatchlist(kinopoiskId = kinopoiskId)

    override suspend fun existsMovieToWatchlist(kinopoiskId: Int): Boolean =
        watchlistDao.existsMovieToWatchlist(kinopoiskId = kinopoiskId)

    override suspend fun getAllLocalRatinglist(): List<RatingEntity> =
        ratingDao.getAllLocalRatinglist()

    override suspend fun saveMyRatingToRatinglist(film: FilmDataDetails, myRating: Int) =
        ratingDao.saveMyRatingToRatinglist(
            film = RatingEntity.fromFilmDataDetails(film = film, myRating = myRating)
        )

    override suspend fun getMyRatingFromRatinglistToDetails(kinopoiskId: Int): RatingEntity =
        ratingDao.getMyRatingFromRatinglistToDetails(kinopoiskId = kinopoiskId)

    override suspend fun deleteMyRatingFromRatinglist(kinopoiskId: Int) =
        ratingDao.deleteMyRatingFromRatinglist(kinopoiskId = kinopoiskId)

    override suspend fun updateMyRatingFromRatinglist(kinopoiskId: Int, myRating: Int) =
        ratingDao.updateMyRatingFromRatinglist(kinopoiskId = kinopoiskId, myRating = myRating)

    override suspend fun existsMyRatingToRatinglist(kinopoiskId: Int): Boolean =
        ratingDao.existsMyRatingToRatinglist(kinopoiskId = kinopoiskId)
}