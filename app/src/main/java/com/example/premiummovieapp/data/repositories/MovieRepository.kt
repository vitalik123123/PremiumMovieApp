package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail
import com.example.premiummovieapp.data.model.MostPopularDataDetail
import com.example.premiummovieapp.data.model.NewMovieDataDetail
import com.example.premiummovieapp.data.model.SeasonEpisodesData
import com.example.premiummovieapp.data.model.TitleData

interface MovieRepository {

    suspend fun getLeaderBoxOffice(): BoxOfficeWeekendDataDetail

    suspend fun getTop10PopularMovies(): List<MostPopularDataDetail>

    suspend fun getTop10PopularTVs(): List<MostPopularDataDetail>

    suspend fun getMostPopularMovies(): List<MostPopularDataDetail>

    suspend fun getMostPopularTVs(): List<MostPopularDataDetail>

    suspend fun getComingSoon(): List<NewMovieDataDetail>

    suspend fun getMoviesDetails(id: String): TitleData

    suspend fun getSeasonEpisodes(id: String, seasonNumber: String): SeasonEpisodesData
}