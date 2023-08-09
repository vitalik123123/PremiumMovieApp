package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.FilmResponseSearchByKeyword
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.FilmTopResponseData
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import com.example.premiummovieapp.data.repositories.local.MovieLocalDataSource
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) :
    MovieRepository {

    override suspend fun getTopFilms(type: String, page: Int): FilmTopResponseData? =
        movieRemoteDataSource.getTopFilms(type = type, page = page).body()

    override suspend fun getFilmDataDetails(id: Int): FilmDataDetails? =
        movieRemoteDataSource.getFilmDataDetails(id = id).body()

    override suspend fun getFilmCast(filmId: Int): List<FilmCast>? =
        movieRemoteDataSource.getFilmCast(filmId = filmId).body()

    override suspend fun getFilmSequelsAndPrequels(id: Int): List<FilmSequelsAndPrequels>? {
        return if (movieRemoteDataSource.getFilmSequelsAndPrequels(id = id).isSuccessful) {
            movieRemoteDataSource.getFilmSequelsAndPrequels(id = id).body()
        } else {
            emptyList()
        }
    }

    override suspend fun getFilmSimilars(id: Int): FilmSimilarsResponseData? {
        return if (movieRemoteDataSource.getFilmSimilars(id = id).isSuccessful) {
            movieRemoteDataSource.getFilmSimilars(id = id).body()
        } else {
            FilmSimilarsResponseData()
        }
    }

    override suspend fun getFilmsSearchByKeyword(
        keyword: String,
        page: Int
    ): FilmResponseSearchByKeyword? {
        return if (movieRemoteDataSource.getFilmsSearchByKeyword(keyword = keyword, page = page)
                .body()!!.listFilms.isNotEmpty()
        ) {
            movieRemoteDataSource.getFilmsSearchByKeyword(keyword = keyword, page = page).body()
        } else {
            FilmResponseSearchByKeyword()
        }
    }

    override suspend fun getAllLocalWatchlist(): List<WatchlistEntity> =
        movieLocalDataSource.getAllLocalWatchlist()

    override suspend fun saveMovieToWatchList(film: FilmDataDetails) =
        movieLocalDataSource.saveMovieToWatchlist(film = film)

    override suspend fun deleteMovieFromWatchlist(kinopoiskId: Int) =
        movieLocalDataSource.deleteMovieFromWatchlist(kinopoiskId = kinopoiskId)

    override suspend fun existsMovieToWatchList(kinopoiskId: Int): Boolean =
        movieLocalDataSource.existsMovieToWatchlist(kinopoiskId = kinopoiskId)
}