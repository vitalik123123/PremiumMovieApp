package com.example.premiummovieapp.presentation.search.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentSearchBinding
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.main.BottomNavigationViewDirections
import com.example.premiummovieapp.presentation.main.findTopNavController
import com.example.premiummovieapp.presentation.main.getAppComponent
import com.example.premiummovieapp.presentation.search.presentation.SearchPagingAdapter
import com.example.premiummovieapp.presentation.search.presentation.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectSearchViewModel().create(stateHandel)
    }
    private val binding: FragmentSearchBinding by viewBinding()
    private val pagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchPagingAdapter()
    }
    private lateinit var layoutManagerSearch: RecyclerView.LayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = true

        binding.svSearchFragment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setKeyword(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        initUi()

        lifecycleScope.launch {
            viewModel.movies.collectLatest(pagingAdapter::submitData)
        }
        pagingAdapter.setListener(onClick)
    }

    private fun initUi() {
        layoutManagerSearch = GridLayoutManager(context, 2)
        binding.rvSearchFragment.layoutManager = layoutManagerSearch
        binding.rvSearchFragment.adapter = pagingAdapter
    }

    private val onClick = object : SearchPagingAdapter.OnItemClickListener {
        override fun onClick(modelId: Int) {
            findTopNavController().navigate(
                BottomNavigationViewDirections.actionBottomNavigationViewToMovieDetailsFragment(
                    modelId
                )
            )
        }
    }
}