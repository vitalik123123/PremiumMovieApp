package com.example.premiummovieapp.presentation.search.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.premiummovieapp.R

class SearchFragment : Fragment(R.layout.fragment_search) {

    companion object {
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}