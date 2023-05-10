package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource): MovieRepository {

    override suspend fun getLeaderBoxOffice(): BoxOfficeWeekendDataDetail = movieRemoteDataSource.getBoxOffice().content.first()

}