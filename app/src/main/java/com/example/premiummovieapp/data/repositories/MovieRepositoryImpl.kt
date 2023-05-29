package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail
import com.example.premiummovieapp.data.model.MostPopularDataDetail
import com.example.premiummovieapp.data.model.NewMovieDataDetail
import com.example.premiummovieapp.data.model.SeasonEpisodesData
import com.example.premiummovieapp.data.model.TitleData
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {

    private suspend fun mostPopularMovies(): List<MostPopularDataDetail> =
        movieRemoteDataSource.getMostPopularMovies().content

    private suspend fun mostPopularTVs(): List<MostPopularDataDetail> =
        movieRemoteDataSource.getMostPopularTVs().content

    override suspend fun getLeaderBoxOffice(): BoxOfficeWeekendDataDetail =
        movieRemoteDataSource.getBoxOffice().content.first()

    override suspend fun getTop10PopularMovies(): List<MostPopularDataDetail> =
        mostPopularMovies().take(10)

    override suspend fun getTop10PopularTVs(): List<MostPopularDataDetail> =
        mostPopularTVs().take(10)

    override suspend fun getMostPopularMovies(): List<MostPopularDataDetail> = mostPopularMovies()

    override suspend fun getMostPopularTVs(): List<MostPopularDataDetail> = mostPopularTVs()

    override suspend fun getComingSoon(): List<NewMovieDataDetail> =
        movieRemoteDataSource.getComingSoon().content

    override suspend fun getMoviesDetails(id: String): TitleData =
        movieRemoteDataSource.getMoviesDetails(id)

    override suspend fun getSeasonEpisodes(id: String, seasonNumber: String): SeasonEpisodesData =
        movieRemoteDataSource.getSeasonEpisodes(id, seasonNumber)
}