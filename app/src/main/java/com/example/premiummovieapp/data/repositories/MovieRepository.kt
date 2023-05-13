package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail
import com.example.premiummovieapp.data.model.MostPopularDataDetail

interface MovieRepository {

    suspend fun getLeaderBoxOffice(): BoxOfficeWeekendDataDetail

    suspend fun getTop10PopularMovies(): List<MostPopularDataDetail>

    suspend fun getTop10PopularTVs(): List<MostPopularDataDetail>
}