package com.example.premiummovieapp.data.model

import com.google.gson.annotations.SerializedName

data class TitleData(

    @SerializedName("id")
    val id: String = "0",

    @SerializedName("title")
    val title: String = "title",

    @SerializedName("type")
    val type: String = "type",

    @SerializedName("year")
    val year: String = "2000",

    @SerializedName("image")
    val image: String = "image",

    @SerializedName("runtimeStr")
    val runtime: String = "1h 00min",

    @SerializedName("plot")
    val description: String = "description",

    @SerializedName("actorList")
    val castList: List<CastList> = emptyList(),

    @SerializedName("genres")
    val genres: String = "genres",

    @SerializedName("companyList")
    val companyList: List<CompanyList> = emptyList(),

    @SerializedName("imDbRating")
    val imdbRating: String = "9.0",

    @SerializedName("similars")
    val moreLikeThisList: List<MoreLikeThisList> = emptyList(),

    @SerializedName("tvSeriesInfo")
    val tvSeriesInfo: TvSeriesInfoList = TvSeriesInfoList(),

    @SerializedName("errorMessage")
    val errorMessage: String = ""
)

data class CastList(

    @SerializedName("id")
    val castId: String = "0",

    @SerializedName("image")
    val castImage: String = "image",

    @SerializedName("name")
    val castName: String = "name",

    @SerializedName("asCharacter")
    val castAsCharacter: String = "character"
)

data class CompanyList(

    @SerializedName("id")
    val companyId: String = "0",

    @SerializedName("name")
    val companyName: String = "name"
)

data class MoreLikeThisList(

    @SerializedName("id")
    val moreLikeThisId: String = "0",

    @SerializedName("title")
    val moreLikeThisTitle: String = "title",

    @SerializedName("image")
    val moreLikeThisImage: String = "image",

    @SerializedName("imDbRating")
    val moreLikeThisImdbRating: String = "9.0"
)

data class TvSeriesInfoList(

    @SerializedName("yearEnd")
    val tvSeriesInfoYearEnd: String = "",

    @SerializedName("seasons")
    val tvSeriesInfoSeasons: List<String> = listOf("1")
)
