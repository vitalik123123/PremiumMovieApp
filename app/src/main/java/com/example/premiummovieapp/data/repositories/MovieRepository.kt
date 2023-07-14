package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.FilmTopResponseData
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData

interface MovieRepository {

    suspend fun getTopFilms(type: String, page: Int): FilmTopResponseData?

    suspend fun getFilmDataDetails(id: Int): FilmDataDetails?

    suspend fun getFilmCast(filmId: Int): List<FilmCast>?

    suspend fun getFilmSequelsAndPrequels(id: Int): List<FilmSequelsAndPrequels>?

    suspend fun getFilmSimilars(id: Int): FilmSimilarsResponseData?
}