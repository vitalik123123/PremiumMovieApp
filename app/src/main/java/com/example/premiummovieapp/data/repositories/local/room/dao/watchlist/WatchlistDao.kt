package com.example.premiummovieapp.data.repositories.local.room.dao.watchlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM ${WatchlistEntity.TABLE_NAME}")
    suspend fun getAllLocalWatchlist(): List<WatchlistEntity>

    @Insert(entity = WatchlistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieToWatchlist(film: WatchlistEntity)

    @Query("DELETE FROM ${WatchlistEntity.TABLE_NAME} WHERE kinopoisk_id = :kinopoiskId")
    suspend fun deleteMovieFromWatchlist(kinopoiskId: Int)

    @Query("SELECT EXISTS(SELECT * FROM ${WatchlistEntity.TABLE_NAME} WHERE kinopoisk_id =:kinopoiskId)")
    suspend fun existsMovieToWatchlist(kinopoiskId: Int): Boolean
}