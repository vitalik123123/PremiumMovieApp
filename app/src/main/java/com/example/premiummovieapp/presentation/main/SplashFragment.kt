package com.example.premiummovieapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.app.MovieApp
import com.example.premiummovieapp.databinding.FragmentSplashBinding
import com.example.premiummovieapp.di.AppComponent
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding: FragmentSplashBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToBottomNavigationView()
            )
        } else {
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToSingInFragment()
            )
        }
    }

    companion object {
        const val PREFS_CURRENT_USER = "prefs_current_user"
        const val FIREBASE_REFERENCE_USERS = "users"
        const val FIREBASE_REFERENCE_RATINGS = "ratings"
        const val FIREBASE_CHILD_USER_DATA = "user_data"
        const val FIREBASE_CHILD_USER_RATING = "user_rating"
        const val REALTIME_DATABASE_NAME =
            "https://premiummovieapp-6f03e-default-rtdb.europe-west1.firebasedatabase.app/"
    }
}

fun Fragment.findTopNavController(): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

fun Fragment.getAppComponent(): AppComponent {
    return (activity?.application as MovieApp).appComponent
}

