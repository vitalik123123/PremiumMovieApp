package com.example.premiummovieapp.presentation.home.fullpopularlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.app.MovieApp
import com.example.premiummovieapp.databinding.FragmentFullPopularListBinding
import com.example.premiummovieapp.presentation.home.fullpopularlist.presentation.ComingSoonListAdapter
import com.example.premiummovieapp.presentation.home.fullpopularlist.presentation.FullPopularListAdapter
import com.example.premiummovieapp.presentation.home.fullpopularlist.presentation.FullPopularListViewModel
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.main.BottomNavigationViewDirections
import com.example.premiummovieapp.presentation.main.connectivity.ConnectivityStatus
import com.example.premiummovieapp.presentation.main.findTopNavController
import com.example.premiummovieapp.presentation.main.getAppComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FullPopularListFragment : Fragment(R.layout.fragment_full_popular_list) {

    private val viewModel: FullPopularListViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectFullPopularListViewModel()
            .create(stateHandel)
    }
    private val binding: FragmentFullPopularListBinding by viewBinding()
    private val args: FullPopularListFragmentArgs by navArgs()
    private var fullPopularAdapter: FullPopularListAdapter = FullPopularListAdapter()
    private lateinit var layoutManagerFullPopular: RecyclerView.LayoutManager
    private var comingSoonAdapter: ComingSoonListAdapter = ComingSoonListAdapter()
    private lateinit var layoutManagerComingSoon: RecyclerView.LayoutManager
    @Inject
    lateinit var connectivityStatus: ConnectivityStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().injectFullPopularListFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = true

        connectivityStatus.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                initUi()
            }else {
                binding.layoutMainFullPopularragment.visibility = View.GONE
                binding.layoutErrorFullPopularFragment.visibility = View.VISIBLE
            }
        }

        binding.backFullPopular.setOnClickListener {
            findNavController().popBackStack()
        }

        fullPopularAdapter.setOnClickListener(onClickPopular)
        comingSoonAdapter.setOnCLickListener(onClickSoon)
    }

    private fun initUi() {
        binding.layoutMainFullPopularragment.visibility = View.VISIBLE
        binding.layoutErrorFullPopularFragment.visibility = View.GONE
        viewModel.state.onEach { state ->
            when (args.openType) {
                MOST_POPULAR_MOVIES -> {
                    binding.rvContentFullPopular.adapter = fullPopularAdapter
                    layoutManagerFullPopular = GridLayoutManager(context, 2)
                    binding.rvContentFullPopular.layoutManager = layoutManagerFullPopular
                    binding.tvHeaderFullPopular.text =
                        resources.getText(R.string.most_popular_movies_this_week)
                    fullPopularAdapter.setData(state.fullPopularListMovies)
                }

                MOST_POPULAR_TVS -> {
                    binding.rvContentFullPopular.adapter = fullPopularAdapter
                    layoutManagerFullPopular = GridLayoutManager(context, 2)
                    binding.rvContentFullPopular.layoutManager = layoutManagerFullPopular
                    binding.tvHeaderFullPopular.text =
                        resources.getText(R.string.most_popular_tvs_this_week)
                    fullPopularAdapter.setData(state.fullPopularListTVs)
                }

                COMING_SOON -> {
                    if (state.comingSoonList.isEmpty()) {
                        viewModel.fetchComingSoonList()
                    }
                    binding.rvContentFullPopular.adapter = comingSoonAdapter
                    layoutManagerComingSoon =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.rvContentFullPopular.layoutManager = layoutManagerComingSoon
                    binding.tvHeaderFullPopular.text = resources.getText(R.string.coming_soon)
                    comingSoonAdapter.setData(state.comingSoonList)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private val onClickSoon = object : ComingSoonListAdapter.OnItemClickListener {
        override fun onClick(modelId: String) {
            actionToMovieDetails(modelId)
        }
    }

    private val onClickPopular = object : FullPopularListAdapter.OnItemClickListener {
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

    companion object {
        const val MOST_POPULAR_MOVIES = "popular_movies"
        const val MOST_POPULAR_TVS = "popular_tvs"
        const val COMING_SOON = "coming_soon"
    }
}