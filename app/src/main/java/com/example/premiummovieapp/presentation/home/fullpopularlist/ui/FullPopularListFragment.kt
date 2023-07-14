package com.example.premiummovieapp.presentation.home.fullpopularlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.api.MovieApi
import com.example.premiummovieapp.databinding.FragmentFullPopularListBinding
import com.example.premiummovieapp.presentation.home.fullpopularlist.presentation.FullPopularListPagingAdapter
import com.example.premiummovieapp.presentation.home.fullpopularlist.presentation.FullPopularListViewModel
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.main.BottomNavigationViewDirections
import com.example.premiummovieapp.presentation.main.connectivity.ConnectivityStatus
import com.example.premiummovieapp.presentation.main.findTopNavController
import com.example.premiummovieapp.presentation.main.getAppComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullPopularListFragment : Fragment(R.layout.fragment_full_popular_list) {

    private val viewModel: FullPopularListViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectFullPopularListViewModel()
            .create(stateHandel)
    }
    private val binding: FragmentFullPopularListBinding by viewBinding()
    private val args: FullPopularListFragmentArgs by navArgs()
    private lateinit var layoutManagerFullPopular: RecyclerView.LayoutManager

    @Inject
    lateinit var connectivityStatus: ConnectivityStatus
    private val pagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FullPopularListPagingAdapter()
    }

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
            } else {
                binding.layoutMainFullPopularragment.visibility = View.GONE
                binding.layoutErrorFullPopularFragment.visibility = View.VISIBLE
            }
        }

        binding.backFullPopular.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            viewModel.movies.collectLatest(pagingAdapter::submitData)
        }
        pagingAdapter.setListener(onClick)
    }

    private fun initUi() {
        binding.layoutMainFullPopularragment.visibility = View.VISIBLE
        binding.layoutErrorFullPopularFragment.visibility = View.GONE
        layoutManagerFullPopular = GridLayoutManager(context, 2)
        binding.rvContentFullPopular.layoutManager = layoutManagerFullPopular
        binding.rvContentFullPopular.adapter = pagingAdapter
        when (args.openType) {
            OpenType.POPULAR_MOVIES -> {
                binding.tvHeaderFullPopular.text =
                    resources.getText(R.string.popular_movies)
                viewModel.setType(MovieApi.TOP_POPULAR)
            }

            OpenType.BEST_MOVIES -> {
                binding.tvHeaderFullPopular.text =
                    resources.getText(R.string.best_movies)
                viewModel.setType(MovieApi.TOP_BEST)
            }

            OpenType.AWAIT_MOVIES -> {
                binding.tvHeaderFullPopular.text =
                    resources.getText(R.string.await_movies)
                viewModel.setType(MovieApi.TOP_AWAIT)
            }
        }
    }

    private val onClick = object : FullPopularListPagingAdapter.OnItemClickListener {
        override fun onClick(modelId: Int) {
            actionToMovieDetails(modelId)
        }
    }

    private fun actionToMovieDetails(id: Int) {
        findTopNavController().navigate(
            BottomNavigationViewDirections.actionBottomNavigationViewToMovieDetailsFragment(
                id
            )
        )
    }

    enum class OpenType {
        POPULAR_MOVIES, BEST_MOVIES, AWAIT_MOVIES
    }
}