package com.example.premiummovieapp.di.modules

import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.data.repositories.MovieRepository
import com.example.premiummovieapp.data.repositories.MovieRepositoryImpl
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource
import com.example.premiummovieapp.data.repositories.remote.retrofit.RetrofitMovieDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieDataModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(movieApi: MovieApi): MovieRemoteDataSource =
        RetrofitMovieDataSource(movieApi = movieApi)

    @Provides
    @Singleton
    fun provideMovieRepository(
        remote: MovieRemoteDataSource
    ): MovieRepository = MovieRepositoryImpl(remote)
}