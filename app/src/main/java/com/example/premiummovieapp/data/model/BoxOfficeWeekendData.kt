package com.example.premiummovieapp.data.model

import com.google.gson.annotations.SerializedName

data class BoxOfficeWeekendData(

    @SerializedName("items")
    val content: List<BoxOfficeWeekendDataDetail> = emptyList(),

    @SerializedName("errorMessage")
    val errorMessage: String = ""
)

data class BoxOfficeWeekendDataDetail(

    @SerializedName("id")
    val id: String = "0",

    @SerializedName("rank")
    val rank: String = "1",

    @SerializedName("title")
    val title: String = "title",

    @SerializedName("image")
    val image: String = "image",

    @SerializedName("weekend")
    val weekend: String = "100.00$",

    @SerializedName("gross")
    val gross: String = "100.00$",

    @SerializedName("weeks")
    val weeks: String = "1"
)
