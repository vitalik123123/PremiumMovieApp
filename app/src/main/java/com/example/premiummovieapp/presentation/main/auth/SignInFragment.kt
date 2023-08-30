package com.example.premiummovieapp.presentation.main.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentSignInBinding
import com.example.premiummovieapp.presentation.main.SplashFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding: FragmentSignInBinding by viewBinding()
    private lateinit var auth: FirebaseAuth
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = this.requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = true

        auth = FirebaseAuth.getInstance()

        binding.btnLoginSignIn.setOnClickListener {
            binding.btnLoginSignInProgressBar.visibility = View.VISIBLE
            binding.btnLoginSignIn.isClickable = false
            lifecycleScope.launch {
                login()
            }
        }

        binding.tvGoToRegistration.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSingInFragmentToSingUpFragment()
            )
        }
    }

    private fun login() {
        val email = binding.etEmailSignIng.text.toString()
        val password = binding.etPasswordSignIn.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                prefs.edit().putString(SplashFragment.PREFS_CURRENT_USER, auth.currentUser!!.uid)
                    .apply()
                findNavController().navigate(
                    SignInFragmentDirections.actionSingInFragmentToBottomNavigationView()
                )
            } else {
                Toast.makeText(context, "Ошибка при входе", Toast.LENGTH_LONG).show()
                binding.btnLoginSignIn.isClickable = true
                binding.btnLoginSignInProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.etEmailSignIng.text?.clear()
        binding.etPasswordSignIn.text?.clear()
        binding.btnLoginSignInProgressBar.visibility = View.GONE
        binding.btnLoginSignIn.isClickable = true
    }

}