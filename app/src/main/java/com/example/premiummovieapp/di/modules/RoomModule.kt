package com.example.premiummovieapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.premiummovieapp.data.database.PremiumMovieRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): PremiumMovieRoomDatabase =
        Room.databaseBuilder(
            context,
            PremiumMovieRoomDatabase::class.java,
            "database_premium_movie"
        )
            .fallbackToDestructiveMigration().build()
}