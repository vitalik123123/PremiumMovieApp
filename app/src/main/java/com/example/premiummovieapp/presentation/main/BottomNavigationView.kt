package com.example.premiummovieapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentBottomNavigationViewBinding

class BottomNavigationView : Fragment(R.layout.fragment_bottom_navigation_view) {

    private val binding: FragmentBottomNavigationViewBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        WindowInsetsControllerCompat(
//            requireActivity().window,
//            requireActivity().window.decorView
//        ).isAppearanceLightStatusBars = true


        val navHost = childFragmentManager.findFragmentById(R.id.containerOnBottomNavigation) as NavHostFragment

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navHost.navController)

            return@setOnItemSelectedListener true
        }
    }

    companion object {
        fun newInstance() = BottomNavigationView().apply {
            arguments = Bundle().apply {}
        }
    }
}