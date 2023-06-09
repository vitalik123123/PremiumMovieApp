package com.example.premiummovieapp.data.api

import com.example.premiummovieapp.data.model.BoxOfficeWeekendData
import com.example.premiummovieapp.data.model.MostPopularData
import com.example.premiummovieapp.data.model.NewMovieData
import com.example.premiummovieapp.data.model.SeasonEpisodesData
import com.example.premiummovieapp.data.model.TitleData
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("BoxOffice/$API_KEY")
    suspend fun getBoxOffice(): BoxOfficeWeekendData

    @GET("MostPopularMovies/$API_KEY")
    suspend fun getMostPopularMovies(): MostPopularData

    @GET("MostPopularTVs/$API_KEY")
    suspend fun getMostPopularTVs(): MostPopularData

    @GET("ComingSoon/$API_KEY")
    suspend fun getComingSoon(): NewMovieData

    @GET("Title/$API_KEY/{id}")
    suspend fun getMoviesDetails(@Path("id") id: String): TitleData

    @GET("SeasonEpisodes/$API_KEY/{id}/{seasonNumber}")
    suspend fun getSeasonEpisodes(
        @Path("id") id: String,
        @Path("seasonNumber") seasonNumber: String
    ): SeasonEpisodesData

    companion object {
        const val API_KEY = "k_bft5158d" // "k_bft5158d" , "k_203vt880"
        const val BASE_URL = "https://imdb-api.com/en/API/"
    }
}