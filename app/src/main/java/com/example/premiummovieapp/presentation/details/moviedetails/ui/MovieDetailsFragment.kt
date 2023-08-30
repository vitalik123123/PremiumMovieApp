package com.example.premiummovieapp.presentation.details.moviedetails.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.premiummovieapp.R
import com.example.premiummovieapp.data.model.details.FilmDataDetails
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForRatingReference
import com.example.premiummovieapp.data.model.firebase.FirebaseRatingForUsersReference
import com.example.premiummovieapp.databinding.FragmentMovieDetailsBinding
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsCastAdapter
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsMoreLikeThisAdapter
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsSequelsAndPrequelsAdapter
import com.example.premiummovieapp.presentation.details.moviedetails.presentation.MovieDetailsViewModel
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.main.SplashFragment
import com.example.premiummovieapp.presentation.main.connectivity.ConnectivityStatus
import com.example.premiummovieapp.presentation.main.getAppComponent
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.sql.Timestamp
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
    private lateinit var prefs: SharedPreferences

    @Inject
    lateinit var connectivityStatus: ConnectivityStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().injectMovieDetailsFragment(this)
        prefs = this.requireActivity().getPreferences(Context.MODE_PRIVATE)
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
        viewModel.exists(args.contentId)
        viewModel.getUserRattingFromRealtimeDatabaseFirebase(
            currentUserUid = prefs.getString(
                SplashFragment.PREFS_CURRENT_USER,
                ""
            )!!, kinopoiskId = args.contentId
        )
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
                tvMovieDetailsRuntime.text =
                    state.film.length.let { length -> length?.toString()?.plus("мин") ?: "0мин" }
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

                ivMovieDetailsAddList.setOnClickListener {
                    if (state.existsMovieToWatchlist) {
                        viewModel.deleteMovieFromWatchlist(args.contentId)
                        viewModel.exists(args.contentId)
                    } else {
                        viewModel.saveMovieToWatchlist(state.film)
                        viewModel.exists(args.contentId)
                    }
                }

                tvMovieDetailsUserRating.setOnClickListener {
                    if (state.userRatingFirebase == null) {
                        showRatingBarDialog(state.film)
                    } else {
                        showUpdateRatingDialog(state.film)
                    }
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

                if (state.existsMovieToWatchlist) {
                    ivMovieDetailsAddList.setImageDrawable(context?.let {
                        AppCompatResources.getDrawable(
                            it, R.drawable.ic_list_selected
                        )
                    })
                } else {
                    ivMovieDetailsAddList.setImageDrawable((context?.let {
                        AppCompatResources.getDrawable(
                            it, R.drawable.ic_list_unselected
                        )
                    }))
                }

                if (state.userRatingFirebase == null) {
                    tvMovieDetailsUserRating.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_star_border,
                        0,
                        0,
                        0
                    )
                } else {
                    tvMovieDetailsUserRating.text = state.userRatingFirebase.toString()
                    tvMovieDetailsUserRating.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_star,
                        0,
                        0,
                        0
                    )
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun showRatingBarDialog(film: FilmDataDetails) {
        val layout = layoutInflater.inflate(R.layout.ratingdialog, null)
        val bar = layout.findViewById(R.id.ratingbar) as RatingBar
        bar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            prefs.edit().putFloat(RATING_PREFS, fl).apply()
        }

        AlertDialog.Builder(requireContext())
            .setView(layout)
            .setTitle(R.string.alert_dialog_ratingbar_ratinglist_title)
            .setNegativeButton("Отмена") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Готово") { dialog, which ->
                saveUserRatingToRealtimeDatabaseFirebase(film = film)
                viewModel.getUserRattingFromRealtimeDatabaseFirebase(
                    currentUserUid = prefs.getString(
                        SplashFragment.PREFS_CURRENT_USER,
                        ""
                    )!!, kinopoiskId = args.contentId
                )
            }
            .show()
    }

    private fun showUpdateRatingDialog(film: FilmDataDetails) {
        val layout = layoutInflater.inflate(R.layout.ratingdialog, null)
        val bar = layout.findViewById(R.id.ratingbar) as RatingBar
        bar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            prefs.edit().putFloat(RATING_PREFS, fl).apply()
        }

        AlertDialog.Builder(requireContext())
            .setView(layout)
            .setTitle(R.string.alert_dialog_update_rating_ratinglist_title)
            .setPositiveButton("Изменить") { dialog, which ->
                saveUserRatingToRealtimeDatabaseFirebase(film = film)
                viewModel.getUserRattingFromRealtimeDatabaseFirebase(
                    currentUserUid = prefs.getString(
                        SplashFragment.PREFS_CURRENT_USER,
                        ""
                    )!!, kinopoiskId = args.contentId
                )
            }
            .setNegativeButton("Удалить") { dialog, which ->
                viewModel.deleteUserRatingToRealtimeDatabaseFirebase(
                    currentUserUid = prefs.getString(
                        SplashFragment.PREFS_CURRENT_USER,
                        ""
                    )!!, kinopoiskId = args.contentId
                )
                viewModel.getUserRattingFromRealtimeDatabaseFirebase(
                    currentUserUid = prefs.getString(
                        SplashFragment.PREFS_CURRENT_USER,
                        ""
                    )!!, kinopoiskId = args.contentId
                )
                binding.tvMovieDetailsUserRating.text = ""
            }
            .setNeutralButton("Отмена") { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    private fun saveUserRatingToRealtimeDatabaseFirebase(film: FilmDataDetails) {
        viewModel.saveUserRatingToRealtimeDatabaseFirebase(
            currentUserUid = prefs.getString(SplashFragment.PREFS_CURRENT_USER, "")!!,
            kinopoiskId = args.contentId,
            ratingForRatingReference = FirebaseRatingForRatingReference(
                uid = prefs.getString(SplashFragment.PREFS_CURRENT_USER, ""),
                kinopoiskId = args.contentId,
                userRating = prefs.getFloat(RATING_PREFS, 0F).toInt(),
                timestamp = Timestamp(System.currentTimeMillis()).time
            ),
            ratingForUsersReference = FirebaseRatingForUsersReference(
                uid = prefs.getString(SplashFragment.PREFS_CURRENT_USER, ""),
                kinopoiskId = args.contentId,
                userRating = prefs.getFloat(RATING_PREFS, 0F).toInt(),
                timestamp = Timestamp(System.currentTimeMillis()).time,
                title = if (film.titleRu != "ТитлеРу") film.titleRu else film.titleOriginal,
                poster = film.poster,
                kinopoiskRating = film.ratingKinopoisk?.toDouble(),
                year = film.year,
                length = film.length,
            )
        )
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
        findNavController().navigate(
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(
                contentId = filmId
            )
        )
    }

    companion object {
        const val RATING_PREFS = "rating"
    }
}