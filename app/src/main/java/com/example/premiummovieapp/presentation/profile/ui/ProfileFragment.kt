package com.example.premiummovieapp.presentation.profile.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.premiummovieapp.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}