package com.example.premiummovieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingDao
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistDao
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity

@Database(
    entities = [
        WatchlistEntity::class, RatingEntity::class
    ],
    version = 2
)
abstract class PremiumMovieRoomDatabase : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao

    abstract fun ratinglistDao(): RatingDao
}