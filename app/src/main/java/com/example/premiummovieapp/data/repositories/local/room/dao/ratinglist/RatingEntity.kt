package com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RatingEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val databaseId: Int,

    @ColumnInfo(name = "kinopoisk_id") val kinopoiskId: Int,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "poster") val poster: String,

    @ColumnInfo(name = "rating") val rating: Double?,

    @ColumnInfo(name = "year") val year: Int?,

    @ColumnInfo(name = "length") val length: Int?,

    @ColumnInfo(name = "my_rating") val myRating: Int
) {

    companion object {
        const val TABLE_NAME = "list_ratinglist_entity_table"

        fun fromFilmDataDetails(film: FilmDataDetails, myRating: Int): RatingEntity = RatingEntity(
            databaseId = 0,
            kinopoiskId = film.id,
            title = if (film.titleRu != "ТитлеРу") film.titleRu else film.titleOriginal,
            poster = film.poster,
            rating = film.ratingKinopoisk?.toDouble(),
            year = film.year,
            length = film.length,
            myRating = myRating
        )
    }
}

