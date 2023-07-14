package com.example.premiummovieapp.presentation.home.fullpopularlist.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.premiummovieapp.data.model.FilmTopResponseFilmsForList
import com.example.premiummovieapp.data.repositories.remote.paging.PagingMoviePageSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FullPopularListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val pagingSourceFactory: PagingMoviePageSource.Factory
) : ViewModel() {

    private val _type = MutableStateFlow("")
    val type: StateFlow<String> = _type.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: StateFlow<PagingData<FilmTopResponseFilmsForList>> = type
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(type: String): Pager<Int, FilmTopResponseFilmsForList> {
        return Pager(PagingConfig(20, enablePlaceholders = false)) {
            pagingSourceFactory.create(type).also { newPagingSource = it }
        }
    }

    fun setType(type: String) {
        _type.tryEmit(type)
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): FullPopularListViewModel
    }
}