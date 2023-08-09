package com.example.premiummovieapp.presentation.home.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentHomeBinding
import com.example.premiummovieapp.presentation.home.fullpopularlist.ui.FullPopularListFragment
import com.example.premiummovieapp.presentation.home.home.presentation.HomeAdapter
import com.example.premiummovieapp.presentation.home.home.presentation.HomeViewModel
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.main.BottomNavigationViewDirections
import com.example.premiummovieapp.presentation.main.connectivity.ConnectivityStatus
import com.example.premiummovieapp.presentation.main.findTopNavController
import com.example.premiummovieapp.presentation.main.getAppComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectHomeViewModel().create(stateHandel)
    }
    private val binding: FragmentHomeBinding by viewBinding()
    private val homeTopPopularMoviesAdapter: HomeAdapter = HomeAdapter()
    private val homeTopBestMoviesAdapter: HomeAdapter = HomeAdapter()
    private val homeTopAwaitMoviesAdapter: HomeAdapter = HomeAdapter()
    private lateinit var layoutManagerPopularMovies: RecyclerView.LayoutManager
    private lateinit var layoutManagerBestMovies: RecyclerView.LayoutManager
    private lateinit var layoutManagerAwaitMovies: RecyclerView.LayoutManager
    @Inject
    lateinit var connectivityStatus: ConnectivityStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().injectHomeFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = false
        connectivityStatus.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                initUi()
            } else {
                binding.layoutMainHomeFragment.visibility = View.GONE
                binding.layoutErrorHomeFragment.visibility = View.VISIBLE
            }
        }

        binding.ivHomeMainMovie.setOnClickListener {
            actionToMovieDetails(viewModel.state.value.leaderFilm.filmId)
        }

        binding.tvSeeAllTopMovies.setOnClickListener {
            actionToFullPopularListFragment(FullPopularListFragment.OpenType.POPULAR_MOVIES)
        }

        binding.tvSeeAllBestMovies.setOnClickListener {
            actionToFullPopularListFragment(FullPopularListFragment.OpenType.BEST_MOVIES)
        }

        binding.tvSeeAllTopAwait.setOnClickListener {
            actionToFullPopularListFragment(FullPopularListFragment.OpenType.AWAIT_MOVIES)
        }

        homeTopPopularMoviesAdapter.setOnClickListener(onClick)
        homeTopBestMoviesAdapter.setOnClickListener(onClick)
        homeTopAwaitMoviesAdapter.setOnClickListener(onClick)
    }

    private fun initUi() {
        binding.rvHomeTopMovies.adapter = homeTopPopularMoviesAdapter
        binding.rvHomeBestMovies.adapter = homeTopBestMoviesAdapter
        binding.rvHomeTopAwait.adapter = homeTopAwaitMoviesAdapter
        layoutManagerPopularMovies =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerBestMovies =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerAwaitMovies =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeTopMovies.layoutManager = layoutManagerPopularMovies
        binding.rvHomeBestMovies.layoutManager = layoutManagerBestMovies
        binding.rvHomeTopAwait.layoutManager = layoutManagerAwaitMovies
        binding.layoutMainHomeFragment.visibility = View.VISIBLE
        binding.layoutErrorHomeFragment.visibility = View.GONE
        viewModel.state.onEach { state ->
            binding.tvHomeTitleMovie.text = state.leaderFilm.titleRu
            Glide.with(this)
                .load(state.leaderFilm.poster)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(binding.ivHomeMainMovie)

            homeTopPopularMoviesAdapter.setData(state.top10PopularMovies)
            homeTopBestMoviesAdapter.setData(state.top10BestMovies)
            homeTopAwaitMoviesAdapter.setData(state.top10AwaitMovies)
        }.launchIn(lifecycleScope)
    }

    private val onClick = object : HomeAdapter.OnItemClickListener {
        override fun onClick(modelId: Int) {
            actionToMovieDetails(modelId)
        }
    }

    private fun actionToMovieDetails(id: Int) {
        findTopNavController().navigate(
            BottomNavigationViewDirections.actionBottomNavigationViewToMovieDetailsFragment(
                contentId = id
            )
        )
    }

    private fun actionToFullPopularListFragment(type: FullPopularListFragment.OpenType) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToFullPopularListFragment(
                openType = type
            )
        )
    }
}
