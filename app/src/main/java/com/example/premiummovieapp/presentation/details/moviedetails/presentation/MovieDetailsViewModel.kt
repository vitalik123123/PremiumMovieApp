package com.example.premiummovieapp.presentation.details.moviedetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.model.details.FilmCast
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.details.FilmSequelsAndPrequels
import com.example.premiummovieapp.data.model.details.FilmSimilars
import com.example.premiummovieapp.data.model.details.ProfessionKey
import com.example.premiummovieapp.data.repositories.MovieRepository
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForRatingReference
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForUsersReference
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

    fun fetchApi(id: Int) {
        viewModelScope.launch {
            state.update { ui ->
                movieRepository.getFilmDataDetails(id).let { content ->
                    ui.copy(
                        film = content!!,
                        genresString = content.genres.joinToString { it.genre },
                        filmCastList = movieRepository.getFilmCast(filmId = id)!!
                            .filter { it.professionKey == ProfessionKey.ACTOR },
                        filmSequelsAndPrequelsList = movieRepository.getFilmSequelsAndPrequels(id = id)!!,
                        filmSimilars = movieRepository.getFilmSimilars(id = id)!!.filmSimilarsList
                    )
                }
            }
        }
    }

    fun exists(kinopoiskId: Int) {
        viewModelScope.launch {
            state.update { ui ->
                ui.copy(
                    existsMovieToWatchlist = movieRepository.existsMovieToWatchList(kinopoiskId = kinopoiskId),
                    existsMovieToRatinglist = movieRepository.existsMyRatingToRatinglist(kinopoiskId = kinopoiskId)
                )
            }
        }
    }

    fun saveMovieToWatchlist(film: FilmDataDetails) {
        viewModelScope.launch {
            movieRepository.saveMovieToWatchList(film = film)
        }
    }

    fun deleteMovieFromWatchlist(kinopoiskId: Int) {
        viewModelScope.launch {
            movieRepository.deleteMovieFromWatchlist(kinopoiskId = kinopoiskId)
        }
    }

    fun saveMyRatingToRatingList(film: FilmDataDetails, myRating: Int) {
        viewModelScope.launch {
            movieRepository.saveMyRatingToRatinglist(film = film, myRating = myRating)
        }
    }

    fun deleteMyRatingFromRatinglist(kinopoiskId: Int) {
        viewModelScope.launch {
            movieRepository.deleteMyRatingFromRatinglist(kinopoiskId = kinopoiskId)
        }
    }

    fun updateMyRatingToRatinglist(kinopoiskId: Int, myRating: Int) {
        viewModelScope.launch {
            movieRepository.updateMyRatingFromRatinglist(
                kinopoiskId = kinopoiskId,
                myRating = myRating
            )
        }
    }

    fun getMyRating(kinopoiskId: Int) {
        viewModelScope.launch {
            state.update { ui ->
                ui.copy(
                    myRating = movieRepository.getMyRatingFromRatinglistToDetails(kinopoiskId = kinopoiskId)
                )
            }
        }
    }

    fun saveUserRatingToRealtimeDatabaseFirebase(
        currentUserUid: String,
        kinopoiskId: Int,
        ratingForRatingReference: FirebaseRatingForRatingReference,
        ratingForUsersReference: FirebaseRatingForUsersReference
    ) {
        viewModelScope.launch {
            movieRepository.saveUserRatingToRealtimeDatabaseFirebase(
                currentUserUid = currentUserUid,
                kinopoiskId = kinopoiskId,
                ratingForRatingReference = ratingForRatingReference,
                ratingForUsersReference = ratingForUsersReference
            )
        }
    }

    fun getUserRattingFromRealtimeDatabaseFirebase(currentUserUid: String, kinopoiskId: Int) {
        viewModelScope.launch {
            state.update { ui ->
                movieRepository.getUserRatingFromRealtimeDatabaseFirebase(
                    currentUserUid = currentUserUid,
                    kinopoiskId = kinopoiskId
                ).let {
                    ui.copy(userRatingFirebase = it?.userRating?.toString())
                }
            }
        }
    }

    fun deleteUserRatingToRealtimeDatabaseFirebase(currentUserUid: String, kinopoiskId: Int) {
        viewModelScope.launch {
            movieRepository.deleteUserRatingToRealtimeDatabaseFirebase(
                currentUserUid = currentUserUid,
                kinopoiskId = kinopoiskId
            )
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): MovieDetailsViewModel
    }

    data class MyState(
        val film: FilmDataDetails = FilmDataDetails(),
        val genresString: String = "",
        val filmCastList: List<FilmCast> = emptyList(),
        val filmSequelsAndPrequelsList: List<FilmSequelsAndPrequels>? = null,
        val filmSimilars: List<FilmSimilars>? = null,
        val existsMovieToWatchlist: Boolean = false,
        val existsMovieToRatinglist: Boolean = false,
        val myRating: Int = 0,
        val userRatingFirebase: String? = null
    )
}