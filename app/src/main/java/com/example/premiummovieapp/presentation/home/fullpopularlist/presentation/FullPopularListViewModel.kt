package com.example.premiummovieapp.presentation.home.fullpopularlist.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.model.MostPopularDataDetail
import com.example.premiummovieapp.data.repositories.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FullPopularListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val state = MutableStateFlow(value = MyState())

    init {
        fetchApi()
    }

    private fun fetchApi() {
        viewModelScope.launch {
            state.update { ui ->
                ui.copy(
                    fullPopularListMovies = movieRepository.getMostPopularMovies(),
                    fullPopularListTVs = movieRepository.getMostPopularTVs()
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): FullPopularListViewModel
    }

    data class MyState(
        val fullPopularListMovies: List<MostPopularDataDetail> = emptyList(),
        val fullPopularListTVs: List<MostPopularDataDetail> = emptyList()
    )
}