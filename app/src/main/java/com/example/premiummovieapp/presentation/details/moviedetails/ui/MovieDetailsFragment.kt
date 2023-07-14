package com.example.premiummovieapp.presentation.details.moviedetails.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsMoreLikeThisAdapter
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsSequelsAndPrequelsAdapter
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
    private val movieDetailsSequelsAndPrequelsAdapter: MovieDetailsSequelsAndPrequelsAdapter =
        MovieDetailsSequelsAndPrequelsAdapter()
    private lateinit var movieDetailsSequelsAndPrequelsLayoutManager: LinearLayoutManager
    private val movieDetailsMoreLikeThisAdapter: MovieDetailsMoreLikeThisAdapter =
        MovieDetailsMoreLikeThisAdapter()
    private lateinit var movieDetailsMoreLikeThisLayoutManager: LinearLayoutManager

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
            } else {
                binding.layoutMainMovieDetailsFragment.visibility = View.GONE
                binding.layoutErrorMovieDetailsFragment.visibility = View.VISIBLE
            }
        }

        movieDetailsSequelsAndPrequelsAdapter.setOnClickListener(onClickSequelsAndPrequels)
        movieDetailsMoreLikeThisAdapter.setOnClickListener(onClickMoreLikeThis)

        binding.backMovieDetails.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initUi() {
        viewModel.fetchApi(args.contentId)
        binding.rvMovieDetailsCast.adapter = movieDetailsCastAdapter
        movieDetailsCastLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieDetailsCast.layoutManager = movieDetailsCastLayoutManager
        binding.rvMovieDetailsSequelsAndPrequels.adapter = movieDetailsSequelsAndPrequelsAdapter
        movieDetailsSequelsAndPrequelsLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieDetailsSequelsAndPrequels.layoutManager =
            movieDetailsSequelsAndPrequelsLayoutManager
        binding.rvMovieDetailsMoreLikeThis.adapter = movieDetailsMoreLikeThisAdapter
        movieDetailsMoreLikeThisLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieDetailsMoreLikeThis.layoutManager = movieDetailsMoreLikeThisLayoutManager
        binding.layoutMainMovieDetailsFragment.visibility = View.VISIBLE
        binding.layoutErrorMovieDetailsFragment.visibility = View.GONE

        viewModel.state.onEach { state ->
            binding.apply {
                tvMovieDetailsTitle.text = state.film.titleRu
                tvMovieDetailsImdbRating.text = if (state.film.ratingKinopoisk != null) {
                    state.film.ratingKinopoisk.toString()
                } else {
                    ""
                }
                tvMovieDetailsYear.text = state.film.year.toString()
                tvMovieDetailsRuntime.text = state.film.length.toString().plus("мин")
                tvMovieDetailsGenres.text =
                    resources.getString(R.string.sample_genres).plus(" ").plus(state.genresString)
                tvMovieDetailsDescription.text = state.film.description

                Glide.with(this@MovieDetailsFragment)
                    .load(state.film.poster)
                    .centerCrop()
                    .into(ivMovieDetailsPoster)

                ivMovieDetailsPoster.setOnClickListener {
                    val list: List<String> = listOf(state.film.poster)
                    StfalconImageViewer.Builder(
                        context, list
                    ) { imageView, image ->
                        Glide.with(requireContext()).load(image).into(imageView!!)
                    }.show()
                }

                movieDetailsCastAdapter.setData(state.filmCastList)
                if (state.filmSequelsAndPrequelsList?.isEmpty() == true) {
                    binding.tvMovieDetailsSequelsAndPrequels.visibility = View.GONE
                    binding.rvMovieDetailsSequelsAndPrequels.visibility = View.GONE
                } else if (state.filmSequelsAndPrequelsList?.isNotEmpty() == true) {
                    movieDetailsSequelsAndPrequelsAdapter.setData(state.filmSequelsAndPrequelsList)
                }

                if (state.filmSimilars?.isEmpty() == true) {
                    binding.tvMovieDetailsMoreLikeThis.visibility = View.GONE
                    binding.rvMovieDetailsMoreLikeThis.visibility = View.GONE
                } else if (state.filmSimilars?.isNotEmpty() == true) {
                    movieDetailsMoreLikeThisAdapter.setData(state.filmSimilars)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private val onClickSequelsAndPrequels =
        object : MovieDetailsSequelsAndPrequelsAdapter.OnItemClickListener {
            override fun onClick(modelId: Int) {
                viewNewData(modelId)
            }
        }

    private val onClickMoreLikeThis = object : MovieDetailsMoreLikeThisAdapter.OnItemClickListener {
        override fun onClick(modelId: Int) {
            viewNewData(modelId)
        }
    }

    private fun viewNewData(filmId: Int) {
        findNavController().navigate(MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(contentId = filmId))

    }
}