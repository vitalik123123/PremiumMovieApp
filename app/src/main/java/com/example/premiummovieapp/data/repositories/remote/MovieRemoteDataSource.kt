package com.example.premiummovieapp.data.repositories.remote

import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.FilmTopResponseData
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import retrofit2.Response

interface MovieRemoteDataSource {

    suspend fun getTopFilms(type: String, page: Int): Response<FilmTopResponseData>

    suspend fun getFilmDataDetails(id: Int): Response<FilmDataDetails>

    suspend fun getFilmCast(filmId: Int): Response<List<FilmCast>>

    suspend fun getFilmSequelsAndPrequels(id: Int): Response<List<FilmSequelsAndPrequels>>

    suspend fun getFilmSimilars(id: Int): Response<FilmSimilarsResponseData>
}