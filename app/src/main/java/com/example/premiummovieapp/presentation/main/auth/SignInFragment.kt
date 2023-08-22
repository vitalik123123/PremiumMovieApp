package com.example.premiummovieapp.presentation.main.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding: FragmentSignInBinding by viewBinding()
    private lateinit var auth: FirebaseAuth

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
            login()
        }

        binding.tvGoToRegistration.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSingInFragmentToSingUpFragment()
            )
        }
    }

    private fun login(){
        val email = binding.etEmailSignIng.text.toString()
        val password = binding.etPasswordSignIn.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(
                    SignInFragmentDirections.actionSingInFragmentToBottomNavigationView()
                )
            } else {
                Toast.makeText(context, "Ошибка при входе", Toast.LENGTH_LONG).show()
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