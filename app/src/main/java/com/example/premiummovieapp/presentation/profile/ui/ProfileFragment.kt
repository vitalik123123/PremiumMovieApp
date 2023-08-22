package com.example.premiummovieapp.presentation.profile.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentProfileBinding
import com.example.premiummovieapp.presentation.main.findTopNavController
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding()
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = true

        auth = FirebaseAuth.getInstance()

        binding.btnProfileLogOut.setOnClickListener {
            auth.signOut()
            findTopNavController().navigateUp()
        }
    }
}