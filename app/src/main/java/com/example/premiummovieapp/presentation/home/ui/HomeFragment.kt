package com.example.premiummovieapp.presentation.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.premiummovieapp.R
import com.example.premiummovieapp.app.MovieApp
import com.example.premiummovieapp.databinding.FragmentHomeBinding
import com.example.premiummovieapp.presentation.home.presentation.HomeViewModel
import com.example.premiummovieapp.presentation.lazyViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by lazyViewModel {stateHandel ->
        (activity?.application as MovieApp).appComponent.injectHomeViewModel().create(stateHandel)
    }
    private val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi(){
        viewModel.state.onEach {state ->
            binding.tvHomeTitleMovie.text = state.leaderBoxOffice.title
            Glide.with(this)
                .load(state.leaderBoxOffice.image)
                .centerCrop()
                .into(binding.ivHomeMainMovie)
        }.launchIn(lifecycleScope)
    }

    companion object {
        fun newInstance() =
            HomeFragment().apply {
            }
    }
}
