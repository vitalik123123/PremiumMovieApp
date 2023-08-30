package com.example.premiummovieapp.presentation.list.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.repositories.MovieRepository
import com.example.premiummovieapp.data.repositories.local.room.dao.ratinglist.RatingEntity
import com.example.premiummovieapp.data.repositories.local.room.dao.watchlist.WatchlistEntity
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForUsersReference
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val state = MutableStateFlow(value = MyState())

    fun getAllLocalLists() {
        viewModelScope.launch {
            movieRepository.getAllLocalWatchlist().let { watchlist ->
                state.update { ui ->
                    ui.copy(
                        watchListList = watchlist.takeLast(10).reversed(),
                        watchlistCount = watchlist.count()
                    )
                }
            }
        }
    }

    fun deleteMovieFromWatchlist(kinopoiskId: Int) {
        viewModelScope.launch {
            movieRepository.deleteMovieFromWatchlist(kinopoiskId = kinopoiskId)
        }
    }

    fun getListUserRatingFromRealtimeDatabaseFirebase(currentUserUid: String) {
        viewModelScope.launch {
            state.update { ui ->
                movieRepository.getListUserRatingFromRealtimeDatabaseFirebase(currentUserUid)
                    .let { list ->
                        ui.copy(
                            firebaseList = list.toList().sortedBy { it.timestamp }.takeLast(10)
                                .reversed(),
                            ratinglistCount = list.count()
                        )
                    }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): ListViewModel
    }

    data class MyState(
        val watchListList: List<WatchlistEntity> = emptyList(),
        val watchlistCount: Int = 0,
        val ratingListList: List<RatingEntity> = emptyList(),
        val ratinglistCount: Int = 0,
        val firebaseList: List<FirebaseRatingForUsersReference> = emptyList()
    )
}