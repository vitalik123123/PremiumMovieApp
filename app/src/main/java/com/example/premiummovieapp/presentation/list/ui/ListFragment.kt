package com.example.premiummovieapp.presentation.list.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.premiummovieapp.R

class ListFragment : Fragment(R.layout.fragment_list) {

    companion object {
        fun newInstance() =
            ListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}