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
    private var homeTopPopularMoviesAdapter: HomeAdapter = HomeAdapter()
    private var homeTopPopularTVsAdapter: HomeAdapter = HomeAdapter()
    private lateinit var layoutManagerPopularMovies: RecyclerView.LayoutManager
    private lateinit var layoutManagerPopularTVs: RecyclerView.LayoutManager
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
            }else {
                binding.layoutMainHomeFragment.visibility = View.GONE
                binding.layoutErrorHomeFragment.visibility = View.VISIBLE
            }
        }

        binding.ivHomeMainMovie.setOnClickListener {
            actionToMovieDetails(viewModel.state.value.leaderBoxOffice.id)
        }

        binding.tvSeeAllTopMovies.setOnClickListener {
            actionToFullPopularListFragment(FullPopularListFragment.MOST_POPULAR_MOVIES)
        }

        binding.tvSeeAllTopTVs.setOnClickListener {
            actionToFullPopularListFragment(FullPopularListFragment.MOST_POPULAR_TVS)
        }

        binding.ivHomeNotification.setOnClickListener {
            actionToFullPopularListFragment(FullPopularListFragment.COMING_SOON)
        }

        homeTopPopularMoviesAdapter.setOnClickListener(onClick)
        homeTopPopularTVsAdapter.setOnClickListener(onClick)
    }

    private fun initUi() {
        binding.rvHomeTopMovies.adapter = homeTopPopularMoviesAdapter
        binding.rvHomeTopTVs.adapter = homeTopPopularTVsAdapter
        layoutManagerPopularMovies =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManagerPopularTVs =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeTopMovies.layoutManager = layoutManagerPopularMovies
        binding.rvHomeTopTVs.layoutManager = layoutManagerPopularTVs
        binding.layoutMainHomeFragment.visibility = View.VISIBLE
        binding.layoutErrorHomeFragment.visibility = View.GONE
        viewModel.state.onEach { state ->
                binding.tvHomeTitleMovie.text = state.leaderBoxOffice.title
                binding.tvHomeEarnMovie.text =
                    resources.getString(R.string.sample_earnings_movie_home).plus(" ")
                        .plus(state.leaderBoxOffice.weekend)
                Glide.with(this)
                    .load(state.leaderBoxOffice.image)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .into(binding.ivHomeMainMovie)

                homeTopPopularMoviesAdapter.setData(state.top10PopularMovies)
                homeTopPopularTVsAdapter.setData(state.top10PopularTVs)
        }.launchIn(lifecycleScope)
    }

    private val onClick = object : HomeAdapter.OnItemClickListener {
        override fun onClick(modelId: String) {
            actionToMovieDetails(modelId)
        }
    }

    private fun actionToMovieDetails(id: String) {
        findTopNavController().navigate(
            BottomNavigationViewDirections.actionBottomNavigationViewToMovieDetailsFragment(
                id
            )
        )
    }

    private fun actionToFullPopularListFragment(type: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToFullPopularListFragment(
                openType = type
            )
        )
    }
}
