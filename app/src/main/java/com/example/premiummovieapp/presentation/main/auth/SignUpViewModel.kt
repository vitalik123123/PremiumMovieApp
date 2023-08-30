package com.example.premiummovieapp.presentation.main.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premiummovieapp.data.model.firebase.FirebaseUser
import com.example.premiummovieapp.data.repositories.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SignUpViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun createUserDataToRealtimeDatabaseFirebase(currentUserUid: String, user: FirebaseUser) {
        viewModelScope.launch {
            movieRepository.saveUserDataToRealtimeDatabaseFirebase(
                currentUserUid = currentUserUid,
                user = user
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SignUpViewModel
    }
}