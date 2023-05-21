package com.example.premiummovieapp.data.repositories.remote

import com.example.premiummovieapp.data.model.BoxOfficeWeekendData
import com.example.premiummovieapp.data.model.MostPopularData
import com.example.premiummovieapp.data.model.NewMovieData

interface MovieRemoteDataSource {

    suspend fun getBoxOffice(): BoxOfficeWeekendData

    suspend fun getMostPopularMovies(): MostPopularData

    suspend fun getMostPopularTVs(): MostPopularData

    suspend fun getComingSoon() : NewMovieData
}