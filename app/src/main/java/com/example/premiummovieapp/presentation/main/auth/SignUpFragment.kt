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
import com.example.premiummovieapp.data.model.firebase.FirebaseUser
import com.example.premiummovieapp.databinding.FragmentSignUpBinding
import com.example.premiummovieapp.presentation.lazyViewModel
import com.example.premiummovieapp.presentation.main.SplashFragment
import com.example.premiummovieapp.presentation.main.getAppComponent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.sql.Timestamp

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by lazyViewModel { stateHandel ->
        getAppComponent().injectSignUpViewModel().create(stateHandel)
    }
    private val binding: FragmentSignUpBinding by viewBinding()
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

        binding.btnRegisterSignUp.setOnClickListener {
            binding.btnRegisterSignUpProgressBar.visibility = View.VISIBLE
            binding.btnRegisterSignUp.isClickable = false
            lifecycleScope.launch {
                signUp()
            }
        }

        binding.tvGoToLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun signUp() {
        val username = binding.etNameSignUp.text.toString()
        val email = binding.etEmailSignUp.text.toString()
        val password = binding.etPasswordSignUp.text.toString()
        val confirmPassword = binding.etConfirmPasswordSignUp.text.toString()

        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(context, "Заполните поля", Toast.LENGTH_LONG).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                prefs.edit().putString(SplashFragment.PREFS_CURRENT_USER, auth.currentUser!!.uid)
                    .apply()
                viewModel.createUserDataToRealtimeDatabaseFirebase(
                    currentUserUid = prefs.getString(
                        SplashFragment.PREFS_CURRENT_USER,
                        ""
                    )!!,
                    user = FirebaseUser(
                        uid = prefs.getString(
                            SplashFragment.PREFS_CURRENT_USER,
                            ""
                        ),
                        username = username,
                        email = email,
                        timestamp = Timestamp(System.currentTimeMillis()).time
                    )
                )
                findNavController().navigate(
                    SignUpFragmentDirections.actionSingUpFragmentToBottomNavigationView()
                )
            } else {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
                binding.btnRegisterSignUp.isClickable = true
                binding.btnRegisterSignUpProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            etNameSignUp.text?.clear()
            etEmailSignUp.text?.clear()
            etPasswordSignUp.text?.clear()
            etConfirmPasswordSignUp.text?.clear()
            btnRegisterSignUpProgressBar.visibility = View.GONE
            btnRegisterSignUp.isClickable = true
        }
    }
}

