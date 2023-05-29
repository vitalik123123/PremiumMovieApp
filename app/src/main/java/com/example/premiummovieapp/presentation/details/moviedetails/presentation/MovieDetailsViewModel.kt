package com.example.premiummovieapp.presentation.details.moviedetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.model.CastList
import com.example.premiummovieapp.data.model.SeasonEpisodesInfo
import com.example.premiummovieapp.data.model.TitleData
import com.example.premiummovieapp.data.repositories.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val state = MutableStateFlow(value = MyState())

    fun fetchApi(id: String) {
        viewModelScope.launch {

            movieRepository.getMoviesDetails(id).let { content ->

                var type = true
                when (content.type) {
                    "Movie" -> type = true
                    "TVSeries" -> {
                        type = false
                        getEpisodes(id = content.id, seasonNumber = "1")
                        state.update { ui ->
                            ui.copy(
                                seasonsList = content.tvSeriesInfo.tvSeriesInfoSeasons.toTypedArray()
                            )
                        }
                    }
                }

                state.update { ui ->
                    ui.copy(
                        content = content,
                        typeMovie = type,
                        company = content.companyList.first().companyName,
                        castList = content.castList.take(10)
                    )
                }
            }
        }
    }

    fun getEpisodes(id: String, seasonNumber: String) {
        viewModelScope.launch {
            movieRepository.getSeasonEpisodes(id, seasonNumber).let { content ->
                state.update { ui ->
                    ui.copy(
                        episodesList = content.episodesInfo
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): MovieDetailsViewModel
    }

    data class MyState(
        val content: TitleData = TitleData(),
        val typeMovie: Boolean = true,
        val company: String = "company",
        val castList: List<CastList> = emptyList(),
        val seasonsList: Array<String> = arrayOf("1"),
        val episodesList: List<SeasonEpisodesInfo> = emptyList()
    )
}