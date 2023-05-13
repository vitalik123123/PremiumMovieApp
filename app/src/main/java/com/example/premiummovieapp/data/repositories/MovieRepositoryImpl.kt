package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail
import com.example.premiummovieapp.data.model.MostPopularDataDetail
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {

    override suspend fun getLeaderBoxOffice(): BoxOfficeWeekendDataDetail =
        movieRemoteDataSource.getBoxOffice().content.first()

    override suspend fun getTop10PopularMovies(): List<MostPopularDataDetail> =
        movieRemoteDataSource.getMostPopularMovies().content.take(10)

    override suspend fun getTop10PopularTVs(): List<MostPopularDataDetail> =
        movieRemoteDataSource.getMostPopularTVs().content.take(10)

}