package com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatingDao {

    @Query("SELECT * FROM ${RatingEntity.TABLE_NAME}")
    suspend fun getAllLocalRatinglist(): List<RatingEntity>

    @Insert(entity = RatingEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMyRatingToRatinglist(film: RatingEntity)

    @Query("SELECT * FROM ${RatingEntity.TABLE_NAME} WHERE kinopoisk_id = :kinopoiskId LIMIT 1")
    suspend fun getMyRatingFromRatinglistToDetails(kinopoiskId: Int): RatingEntity

    @Query("DELETE FROM ${RatingEntity.TABLE_NAME} WHERE kinopoisk_id = :kinopoiskId")
    suspend fun deleteMyRatingFromRatinglist(kinopoiskId: Int)

    @Query("UPDATE ${RatingEntity.TABLE_NAME} SET my_rating = :myRating WHERE kinopoisk_id = :kinopoiskId")
    suspend fun updateMyRatingFromRatinglist(kinopoiskId: Int, myRating: Int)

    @Query("SELECT EXISTS(SELECT * FROM ${RatingEntity.TABLE_NAME} WHERE kinopoisk_id = :kinopoiskId)")
    suspend fun existsMyRatingToRatinglist(kinopoiskId: Int): Boolean
}