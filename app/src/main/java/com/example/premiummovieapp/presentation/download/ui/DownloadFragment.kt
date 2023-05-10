package com.example.premiummovieapp.presentation.download.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.premiummovieapp.R

class DownloadFragment : Fragment(R.layout.fragment_download) {

    companion object {
        fun newInstance() =
            DownloadFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}