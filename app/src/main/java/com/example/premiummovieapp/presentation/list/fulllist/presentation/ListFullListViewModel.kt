package com.example.premiummovieapp.presentation.list.fulllist.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.repositories.MovieRepository
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListFullListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val state = MutableStateFlow(value = MyState())

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): ListFullListViewModel
    }

    fun getLocalFullWatchlist() {
        viewModelScope.launch {
            state.update { ui ->
                ui.copy(
                    fullWatchlist = movieRepository.getAllLocalWatchlist().reversed()
                )
            }
        }
    }

    fun deleteMovieFromWatchlist(kinopoiskId: Int) {
        viewModelScope.launch {
            movieRepository.deleteMovieFromWatchlist(kinopoiskId = kinopoiskId)
        }
    }

    data class MyState(
        val fullWatchlist: List<WatchlistEntity> = emptyList()
    )
}