package com.example.premiummovieapp.data.repositories.local.room.dao.watchlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class WatchlistEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val databaseId: Int,

    @ColumnInfo(name = "kinopoisk_id") val kinopoiskId: Int,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "poster") val poster: String,

    @ColumnInfo(name = "rating") val rating: Double?,

    @ColumnInfo(name = "year") val year: Int?,

    @ColumnInfo(name = "length") val length: Int?
) {

    fun toFilmDataDetails(): FilmDataDetails = FilmDataDetails(
        id = kinopoiskId,
        titleRu = title,
        titleOriginal = title,
        poster = poster,
        ratingKinopoisk = rating,
        year = year,
        length = length
    )

    companion object {
        const val TABLE_NAME = "list_watchlist_entity_table"

        fun fromFilmDataDetails(film: FilmDataDetails): WatchlistEntity = WatchlistEntity(
            databaseId = 0,
            kinopoiskId = film.id,
            title = if (film.titleRu != "ТитлеРу") film.titleRu else film.titleOriginal,
            poster = film.poster,
            rating = film.ratingKinopoisk?.toDouble(),
            year = film.year,
            length = film.length
        )
    }

}
