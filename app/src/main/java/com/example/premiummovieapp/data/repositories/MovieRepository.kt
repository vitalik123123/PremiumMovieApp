package com.example.premiummovieapp.data.repositories

import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail

interface MovieRepository {

    suspend fun getLeaderBoxOffice(): BoxOfficeWeekendDataDetail
}