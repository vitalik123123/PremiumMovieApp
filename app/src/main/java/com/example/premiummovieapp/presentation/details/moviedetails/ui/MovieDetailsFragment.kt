package com.example.premiummovieapp.presentation.details.moviedetails.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentMovieDetailsBinding
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsCastAdapter
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsEpisodesAdapter
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsMoreLikeThisAdapter
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsViewModel
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.main.connectivity.ConnectivityStatus
import com.example.premiummovieapp.presentation.main.getAppComponent
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectMovieDetailsViewModel().create(stateHandel)
    }
    private val binding: FragmentMovieDetailsBinding by viewBinding()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movieDetailsCastAdapter: MovieDetailsCastAdapter = MovieDetailsCastAdapter()
    private lateinit var movieDetailsCastLayoutManager: LinearLayoutManager
    private val movieDetailsEpisodesAdapter: MovieDetailsEpisodesAdapter =
        MovieDetailsEpisodesAdapter()
    private lateinit var movieDetailsEpisodesLayoutManager: LinearLayoutManager
    private val movieDetailsMoreLikeThisAdapter: MovieDetailsMoreLikeThisAdapter =
        MovieDetailsMoreLikeThisAdapter()
    private lateinit var movieDetailsMoreLikeThisLayoutManager: LinearLayoutManager
    private var currentSeason: String = "1"
    @Inject
    lateinit var connectivityStatus: ConnectivityStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().injectMovieDetailsFragment(this)
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
                binding.layoutMainMovieDetailsFragment.visibility = View.GONE
                binding.layoutErrorMovieDetailsFragment.visibility = View.VISIBLE
            }
        }

        binding.backMovieDetails.setOnClickListener {
            findNavController().popBackStack()
        }

        movieDetailsMoreLikeThisAdapter.setOnClickListener(onClickMoreLikeThis)
    }

    private fun initUi() {
        viewModel.fetchApi(args.contentId)
        binding.rvMovieDetailsCast.adapter = movieDetailsCastAdapter
        movieDetailsCastLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieDetailsCast.layoutManager = movieDetailsCastLayoutManager
        binding.rvMovieDetailsMoreLikeThis.adapter = movieDetailsMoreLikeThisAdapter
        movieDetailsMoreLikeThisLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieDetailsMoreLikeThis.layoutManager = movieDetailsMoreLikeThisLayoutManager
        binding.layoutMainMovieDetailsFragment.visibility = View.VISIBLE
        binding.layoutErrorMovieDetailsFragment.visibility = View.GONE

        viewModel.state.onEach { state ->
            binding.apply {
                if (state.typeMovie) {
                    frameMovieDetailsTVsContent.visibility = View.GONE
                } else {
                    frameMovieDetailsTVsContent.visibility = View.VISIBLE
                    tvMovieDetailsChooseSeason.text =
                        resources.getString(R.string.choose_season_movie_details)
                            .plus(" $currentSeason")
                    binding.rvMovieDetailsEpisodes.adapter = movieDetailsEpisodesAdapter
                    movieDetailsEpisodesLayoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvMovieDetailsEpisodes.layoutManager = movieDetailsEpisodesLayoutManager
                    movieDetailsEpisodesAdapter.setData(state.episodesList)
                    tvMovieDetailsChooseSeason.setOnClickListener {
                        showListSeasonAlert(state.seasonsList, state.content.id)
                    }
                }
                tvMovieDetailsTitle.text = state.content.title

                tvMovieDetailsImdbRating.text = state.content.imdbRating
                tvMovieDetailsYear.text = state.content.year
                tvMovieDetailsRuntime.text = state.content.runtime
                tvMovieDetailsCompany.text = state.company

                tvMovieDetailsGenres.text =
                    resources.getString(R.string.sample_genres).plus(" ").plus(state.content.genres)

                tvMovieDetailsDescription.text = state.content.description

                Glide.with(this@MovieDetailsFragment)
                    .load(state.content.image)
                    .centerCrop()
                    .into(ivMovieDetailsPoster)

                ivMovieDetailsPoster.setOnClickListener {
                    val list: List<String> = listOf(state.content.image)
                    StfalconImageViewer.Builder(
                        context, list
                    ) { imageView, image ->
                        Glide.with(requireContext()).load(image).into(imageView!!)
                    }.show()
                }

                movieDetailsCastAdapter.setData(state.castList)
                movieDetailsMoreLikeThisAdapter.setData(state.moreLikeThisList)
            }
        }.launchIn(lifecycleScope)
    }

    private val onClickMoreLikeThis = object : MovieDetailsMoreLikeThisAdapter.OnItemClickListener {
        override fun onClick(modelId: String) {
            currentSeason = "1"
            binding.rvMovieDetailsCast.scrollToPosition(0)
            binding.rvMovieDetailsEpisodes.scrollToPosition(0)
            binding.rvMovieDetailsMoreLikeThis.scrollToPosition(0)
            viewModel.fetchApi(modelId)
        }

    }

    private fun showListSeasonAlert(list: Array<String>, id: String) {
        AlertDialog.Builder(this.context)
            .setTitle(resources.getString(R.string.choose_season_movie_details_alert_dialog))
            .setItems(list) { _, which ->
                if (currentSeason != list[which]) {
                    binding.tvMovieDetailsChooseSeason.text =
                        resources.getString(R.string.choose_season_movie_details)
                            .plus(" ${list[which]}")
                    viewModel.getEpisodes(id = id, seasonNumber = list[which])
                    currentSeason = list[which]
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show()
            }
            .show()
    }
}