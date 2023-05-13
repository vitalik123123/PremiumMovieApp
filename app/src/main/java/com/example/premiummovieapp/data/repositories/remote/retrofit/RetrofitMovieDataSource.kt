package com.example.premiummovieapp.data.repositories.remote.retrofit

import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.data.model.BoxOfficeWeekendData
import com.example.premiummovieapp.data.model.MostPopularData
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource

class RetrofitMovieDataSource(private val movieApi: MovieApi): MovieRemoteDataSource {

    override suspend fun getBoxOffice(): BoxOfficeWeekendData = movieApi.getBoxOffice()

    override suspend fun getMostPopularMovies(): MostPopularData = movieApi.getMostPopularMovies()

    override suspend fun getMostPopularTVs(): MostPopularData = movieApi.getMostPopularTVs()
}