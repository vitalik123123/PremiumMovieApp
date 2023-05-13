package com.example.premiummovieapp.presentation.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail
import com.example.premiummovieapp.data.model.MostPopularDataDetail
import com.example.premiummovieapp.data.repositories.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
): ViewModel() {

    val state = MutableStateFlow(value = MyState())

    init {
        fetchApi()
    }

    private fun fetchApi(){
        viewModelScope.launch {
            state.update { ui ->
                ui.copy(
                    leaderBoxOffice = movieRepository.getLeaderBoxOffice(),
                    top10PopularMovies = movieRepository.getTop10PopularMovies(),
                    top10PopularTVs = movieRepository.getTop10PopularTVs()
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): HomeViewModel
    }

    data class MyState(
        val leaderBoxOffice: BoxOfficeWeekendDataDetail = BoxOfficeWeekendDataDetail(),
        val top10PopularMovies: List<MostPopularDataDetail> = emptyList(),
        val top10PopularTVs: List<MostPopularDataDetail> = emptyList()
    )
}