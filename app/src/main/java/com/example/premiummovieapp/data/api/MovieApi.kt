package com.example.premiummovieapp.data.api

import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.FilmTopResponseData
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilarsResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("api/v2.2/films/top")
    @Headers(HEADERS)
    suspend fun getTopFilms(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<FilmTopResponseData>

    @GET("api/v2.2/films/{id}")
    @Headers(HEADERS)
    suspend fun getFilmDataDetails(
        @Path("id") id: Int
    ): Response<FilmDataDetails>

    @GET("api/v1/staff")
    @Headers(HEADERS)
    suspend fun getFilmCast(
        @Query("filmId") filmId: Int
    ): Response<List<FilmCast>>

    @GET("api/v2.1/films/{id}/sequels_and_prequels")
    @Headers(HEADERS)
    suspend fun getFilmSequelsAndPrequels(
        @Path("id") id: Int
    ): Response<List<FilmSequelsAndPrequels>>

    @GET("api/v2.2/films/{id}/similars")
    @Headers(HEADERS)
    suspend fun getFilmSimilars(
        @Path("id") id: Int
    ): Response<FilmSimilarsResponseData>

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
        const val HEADERS = "X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b" // 03ef47cc-dde2-4fda-acae-7f4e0865cfef
        const val TOP_BEST = "TOP_250_BEST_FILMS"
        const val TOP_POPULAR = "TOP_100_POPULAR_FILMS"
        const val TOP_AWAIT = "TOP_AWAIT_FILMS"
    }
}