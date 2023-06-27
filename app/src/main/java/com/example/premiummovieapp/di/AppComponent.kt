package com.example.premiummovieapp.di

import android.content.Context
import com.example.premiummovieapp.di.modules.MovieDataModule
import com.example.premiummovieapp.di.modules.RemoteModule
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsViewModel
import com.example.premiummovieapp.presentation.details.moviedetails.ui.MovieDetailsFragment
import com.example.premiummovieapp.presentation.home.fullpopularlist.presentation.FullPopularListViewModel
import com.example.premiummovieapp.presentation.home.fullpopularlist.ui.FullPopularListFragment
import com.example.premiummovieapp.presentation.home.home.presentation.HomeViewModel
import com.example.premiummovieapp.presentation.home.home.ui.HomeFragment
import com.example.premiummovieapp.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RemoteModule::class, MovieDataModule::class])
@Singleton
interface AppComponent {
    fun injectHomeFragment(fragment: HomeFragment)
    fun injectMovieDetailsFragment(fragment: MovieDetailsFragment)
    fun injectFullPopularListFragment(fragment: FullPopularListFragment)
    fun injectMainActivity(activity: MainActivity)
    fun injectHomeViewModel(): HomeViewModel.Factory
    fun injectFullPopularListViewModel(): FullPopularListViewModel.Factory
    fun injectMovieDetailsViewModel(): MovieDetailsViewModel.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}