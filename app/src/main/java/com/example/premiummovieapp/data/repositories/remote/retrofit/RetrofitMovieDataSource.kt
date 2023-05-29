package com.example.premiummovieapp.data.repositories.remote.retrofit

import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.data.model.BoxOfficeWeekendData
import com.example.premiummovieapp.data.model.MostPopularData
import com.example.premiummovieapp.data.model.NewMovieData
import com.example.premiummovieapp.data.model.SeasonEpisodesData
import com.example.premiummovieapp.data.model.TitleData
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource

class RetrofitMovieDataSource(private val movieApi: MovieApi) : MovieRemoteDataSource {

    override suspend fun getBoxOffice(): BoxOfficeWeekendData = movieApi.getBoxOffice()

    override suspend fun getMostPopularMovies(): MostPopularData = movieApi.getMostPopularMovies()

    override suspend fun getMostPopularTVs(): MostPopularData = movieApi.getMostPopularTVs()

    override suspend fun getComingSoon(): NewMovieData = movieApi.getComingSoon()

    override suspend fun getMoviesDetails(id: String): TitleData = movieApi.getMoviesDetails(id)

    override suspend fun getSeasonEpisodes(id: String, seasonNumber: String): SeasonEpisodesData =
        movieApi.getSeasonEpisodes(id, seasonNumber)
}