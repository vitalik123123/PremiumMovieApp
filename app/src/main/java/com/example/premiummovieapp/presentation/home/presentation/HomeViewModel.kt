package com.example.premiummovieapp.presentation.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.model.BoxOfficeWeekendDataDetail
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
        fetchLeaderBoxOffice()
    }

    private fun fetchLeaderBoxOffice(){
        viewModelScope.launch {
            state.update { ui ->
                ui.copy(
                    leaderBoxOffice = movieRepository.getLeaderBoxOffice()
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): HomeViewModel
    }

    data class MyState(
        val leaderBoxOffice: BoxOfficeWeekendDataDetail = BoxOfficeWeekendDataDetail()
    )
}