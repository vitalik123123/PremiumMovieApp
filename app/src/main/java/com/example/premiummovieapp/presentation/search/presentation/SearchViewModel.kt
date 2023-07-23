package com.example.premiummovieapp.presentation.search.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.premiummovieapp.data.model.FilmsListSearchByKeyword
import com.example.premiummovieapp.data.repositories.remote.paging.PagingMoviesSearchPageSource
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

class SearchViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val pagingSourceFactory: PagingMoviesSearchPageSource.Factory
) : ViewModel() {

    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: StateFlow<PagingData<FilmsListSearchByKeyword>> = keyword
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(keyword: String): Pager<Int, FilmsListSearchByKeyword> {
        return Pager(PagingConfig(20, enablePlaceholders = false)) {
            pagingSourceFactory.create(keyword).also { newPagingSource = it }
        }
    }

    fun setKeyword(keyword: String) {
        _keyword.tryEmit(keyword)
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchViewModel
    }
}