package com.example.premiummovieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistDao
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity

@Database(
    entities = [
        WatchlistEntity::class
    ],
    version = 1
)
abstract class PremiumMovieRoomDatabase : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao
}