package com.example.premiummovieapp.app

import android.app.Application
import com.example.premiummovieapp.di.AppComponent
import com.example.premiummovieapp.di.DaggerAppComponent

class MovieApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }
}