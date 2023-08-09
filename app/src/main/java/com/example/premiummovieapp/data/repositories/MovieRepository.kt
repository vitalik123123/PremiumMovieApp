package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.FilmResponseSearchByKeyword
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.FilmTopResponseData
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity

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
}