package com.example.premiummovieapp.data.repositories.local.room

import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.repositories.local.MovieLocalDataSource
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistDao
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity

class RoomMovieDataSource(
    private val watchlistDao: WatchlistDao
) : MovieLocalDataSource {

    override suspend fun getAllLocalWatchlist(): List<WatchlistEntity> =
        watchlistDao.getAllLocalWatchlist()

    override suspend fun saveMovieToWatchlist(film: FilmDataDetails) =
        watchlistDao.saveMovieToWatchlist(film = WatchlistEntity.fromFilmDataDetails(film = film))

    override suspend fun deleteMovieFromWatchlist(kinopoiskId: Int) =
        watchlistDao.deleteMovieFromWatchlist(kinopoiskId = kinopoiskId)

    override suspend fun existsMovieToWatchlist(kinopoiskId: Int): Boolean =
        watchlistDao.existsMovieToWatchlist(kinopoiskId = kinopoiskId)
}