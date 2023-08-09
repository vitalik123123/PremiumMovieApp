package com.example.premiummovieapp.presentation.list.fulllist.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentListFullListBinding
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.list.fulllist.presentation.ListFullListViewModel
import com.example.premiummovieapp.presentation.list.fulllist.presentation.ListFullWatchlistAdapter
import com.example.premiummovieapp.presentation.main.BottomNavigationViewDirections
import com.example.premiummovieapp.presentation.main.findTopNavController
import com.example.premiummovieapp.presentation.main.getAppComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFullListFragment : Fragment(R.layout.fragment_list_full_list) {

    private val viewModel: ListFullListViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectListFullListViewModel().create(stateHandel)
    }
    private val binding: FragmentListFullListBinding by viewBinding()
    private val listFullWatchlistAdapter: ListFullWatchlistAdapter = ListFullWatchlistAdapter()
    private lateinit var layoutManagerFullWatchlist: RecyclerView.LayoutManager
    private val args: ListFullListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = true

        initUi()

        binding.backListFullList.setOnClickListener {
            findNavController().popBackStack()
        }

        listFullWatchlistAdapter.setOnClickListener(onCLick)
    }

    private fun initUi() {
        binding.rvListFullList.adapter = listFullWatchlistAdapter
        layoutManagerFullWatchlist =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvListFullList.layoutManager = layoutManagerFullWatchlist
        viewModel.state.onEach { state ->

            when (args.listType) {
                ListType.Watchlist -> {
                    binding.tvNameListFullList.text = resources.getText(R.string.watchlist)
                    viewModel.getLocalFullWatchlist()
                    listFullWatchlistAdapter.setData(state.fullWatchlist)
                }

                ListType.RatingList -> {

                }
            }
        }.launchIn(lifecycleScope)
    }

    private val onCLick = object : ListFullWatchlistAdapter.OnItemClickListener {
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
                viewModel.getLocalFullWatchlist()
            }
            .setNegativeButton("NO") { dialog, which ->

            }
            .show()
    }

    enum class ListType {
        Watchlist, RatingList
    }
}