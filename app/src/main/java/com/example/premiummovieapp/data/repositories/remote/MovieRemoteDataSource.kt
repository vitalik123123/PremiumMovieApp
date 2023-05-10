package com.example.premiummovieapp.data.repositories.remote

import com.example.premiummovieapp.data.model.BoxOfficeWeekendData

interface MovieRemoteDataSource {

    suspend fun getBoxOffice(): BoxOfficeWeekendData
}