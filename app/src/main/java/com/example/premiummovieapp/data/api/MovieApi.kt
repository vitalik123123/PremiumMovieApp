package com.example.premiummovieapp.data.api

import com.example.premiummovieapp.data.model.BoxOfficeWeekendData
import retrofit2.http.GET

interface MovieApi {

    @GET("BoxOffice/$API_KEY")
    suspend fun getBoxOffice(): BoxOfficeWeekendData

    companion object {
        const val API_KEY = "k_bft5158d"
        const val BASE_URL = "https://imdb-api.com/en/API/"
    }
}