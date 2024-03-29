package com.example.premiummovieapp.di.modules

import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.data.database.PremiumMovieRoomDatabase
import com.example.premiummovieapp.data.repositories.MovieRepository
import com.example.premiummovieapp.data.repositories.MovieRepositoryImpl
import com.example.premiummovieapp.data.repositories.local.MovieLocalDataSource
import com.example.premiummovieapp.data.repositories.local.room.RoomMovieDataSource
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSource
import com.example.premiummovieapp.data.repositories.remote.MovieRemoteDataSourceImpl
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieDataModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        movieApi: MovieApi,
        firebaseDatabase: FirebaseDatabase
    ): MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(movieApi = movieApi, firebaseDatabase = firebaseDatabase)

    @Provides
    @Singleton
    fun provideLocalDataSource(roomDatabase: PremiumMovieRoomDatabase): MovieLocalDataSource =
        RoomMovieDataSource(roomDatabase.watchlistDao(), roomDatabase.ratinglistDao())

    @Provides
    @Singleton
    fun provideMovieRepository(
        remote: MovieRemoteDataSource,
        local: MovieLocalDataSource
    ): MovieRepository = MovieRepositoryImpl(
        movieRemoteDataSource = remote,
        movieLocalDataSource = local
    )
}