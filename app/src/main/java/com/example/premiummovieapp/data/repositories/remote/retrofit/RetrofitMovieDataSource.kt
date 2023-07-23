package com.example.premiummovieapp.data.repositories.remote.retrofit

import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.data.model.FilmResponseSearchByKeyword
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource
import retrofit2.Response

class RetrofitMovieDataSource(private val movieApi: MovieApi) : MovieRemoteDataSource {

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
}