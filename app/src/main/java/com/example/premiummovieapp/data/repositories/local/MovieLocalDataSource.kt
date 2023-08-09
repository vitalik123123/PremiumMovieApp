package com.example.premiummovieapp.data.repositories.local

import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity

interface MovieLocalDataSource {

    suspend fun getAllLocalWatchlist(): List<WatchlistEntity>

    suspend fun saveMovieToWatchlist(film: FilmDataDetails)

    suspend fun deleteMovieFromWatchlist(kinopoiskId: Int)

    suspend fun existsMovieToWatchlist(kinopoiskId: Int): Boolean
}