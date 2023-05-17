package com.example.premiummovieapp.di

import android.content.Context
import com.example.premiummovieapp.di.modules.MovieDataModule
import com.example.premiummovieapp.di.modules.RemoteModule
import com.example.premiummovieapp.presentation.home.fullpopularlist.presentation.FullPopularListViewModel
import com.example.premiummovieapp.presentation.home.home.presentation.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RemoteModule::class, MovieDataModule::class])
@Singleton
interface AppComponent {
    fun injectHomeViewModel(): HomeViewModel.Factory
    fun injectFullPopularListViewModel(): FullPopularListViewModel.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}