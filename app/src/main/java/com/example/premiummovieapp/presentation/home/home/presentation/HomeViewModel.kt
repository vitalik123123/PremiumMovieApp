package com.example.premiummovieapp.presentation.home.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.data.model.FilmTopResponseFilmsForList
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
) : ViewModel() {

    val state = MutableStateFlow(value = MyState())

    init {
        fetchApi()
    }

    private fun fetchApi() {
        viewModelScope.launch {
            state.update { ui ->
                ui.copy(
                    leaderFilm = movieRepository.getTopFilms(
                        type = MovieApi.TOP_POPULAR,
                        page = 1
                    )!!.films.first(),
                    top10PopularMovies = movieRepository.getTopFilms(
                        type = MovieApi.TOP_POPULAR,
                        page = 1
                    )!!.films.take(10),
                    top10BestMovies = movieRepository.getTopFilms(
                        type = MovieApi.TOP_BEST,
                        page = 1
                    )!!.films.take(10),
                    top10AwaitMovies = movieRepository.getTopFilms(
                        type = MovieApi.TOP_AWAIT,
                        page = 1
                    )!!.films.take(10)
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): HomeViewModel
    }

    data class MyState(
        val leaderFilm: FilmTopResponseFilmsForList = FilmTopResponseFilmsForList(),
        val top10PopularMovies: List<FilmTopResponseFilmsForList> = emptyList(),
        val top10BestMovies: List<FilmTopResponseFilmsForList> = emptyList(),
        val top10AwaitMovies: List<FilmTopResponseFilmsForList> = emptyList()
    )
}