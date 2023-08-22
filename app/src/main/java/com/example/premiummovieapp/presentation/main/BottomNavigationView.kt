package com.example.premiummovieapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.app.MovieApp
import com.example.premiummovieapp.databinding.FragmentBottomNavigationViewBinding
import com.example.premiummovieapp.di.AppComponent

class BottomNavigationView : Fragment(R.layout.fragment_bottom_navigation_view) {

    private val binding: FragmentBottomNavigationViewBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost =
            childFragmentManager.findFragmentById(R.id.containerOnBottomNavigation) as NavHostFragment

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navHost.navController)

            return@setOnItemSelectedListener true
        }
    }
}