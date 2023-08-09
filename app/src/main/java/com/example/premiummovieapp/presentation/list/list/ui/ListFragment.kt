package com.example.premiummovieapp.presentation.list.list.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentListBinding
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.list.fulllist.ui.ListFullListFragment
import com.example.premiummovieapp.presentation.list.list.presentation.ListViewModel
import com.example.premiummovieapp.presentation.list.list.presentation.ListWatchlistAdapter
import com.example.premiummovieapp.presentation.main.BottomNavigationViewDirections
import com.example.premiummovieapp.presentation.main.findTopNavController
import com.example.premiummovieapp.presentation.main.getAppComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: ListViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectListViewModel().create(stateHandel)
    }
    private val binding: FragmentListBinding by viewBinding()
    private val listWatchlistAdapter: ListWatchlistAdapter = ListWatchlistAdapter()
    private lateinit var layoutManagerWatchlist: RecyclerView.LayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = true

        initUi()

        binding.tvListWatchlistSeeAll.setOnClickListener {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToListFullListFragment(ListFullListFragment.ListType.Watchlist)
            )
        }

        listWatchlistAdapter.setOnClickListener(onClick)
    }

    private fun initUi() {
        binding.rvListWatchlist.adapter = listWatchlistAdapter
        layoutManagerWatchlist = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListWatchlist.layoutManager = layoutManagerWatchlist
        viewModel.state.onEach { state ->
            binding.tvListWatchlistCount.text = state.watchlistCount.toString()
            listWatchlistAdapter.setData(state.watchListList)
        }.launchIn(lifecycleScope)
    }

    private val onClick = object : ListWatchlistAdapter.OnItemClickListener {
        override fun onClick(modelId: Int) {
            findTopNavController().navigate(
                BottomNavigationViewDirections.actionBottomNavigationViewToMovieDetailsFragment(
                    contentId = modelId
                )
            )
        }

        override fun onClickRemove(modelId: Int, modelTitle: String) {
            showRemoveAlertDialog(kinopoiskId = modelId, title = modelTitle)
        }
    }

    private fun showRemoveAlertDialog(kinopoiskId: Int, title: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getText(R.string.alert_dialog_remove_watchlist_title))
            .setMessage(
                resources.getString(R.string.alert_dialog_remove_watchlist_message).plus(" ")
                    .plus(title)
            )
            .setPositiveButton("YES") { dialog, which ->
                viewModel.deleteMovieFromWatchlist(kinopoiskId = kinopoiskId)
                viewModel.getAllLocalWatchlist()
            }
            .setNegativeButton("NO") { dialog, which ->

            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLocalWatchlist()
    }

}