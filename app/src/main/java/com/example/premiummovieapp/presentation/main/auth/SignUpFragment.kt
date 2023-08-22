package com.example.premiummovieapp.presentation.main.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.premiummovieapp.R
import com.example.premiummovieapp.databinding.FragmentSignUpBinding
import com.example.premiummovieapp.presentation.main.SplashFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding: FragmentSignUpBinding by viewBinding()
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowInsetsControllerCompat(
            requireActivity().window,
            view
        ).isAppearanceLightStatusBars = true

        auth = FirebaseAuth.getInstance()
        reference = Firebase.database(SplashFragment.REALTIME_DATABASE_NAME)
            .getReference(SplashFragment.FIREBASE_CHILD_USERS)

        binding.btnRegisterSignUp.setOnClickListener {
            binding.btnRegisterSignUpProgressBar.visibility = View.VISIBLE
            binding.btnRegisterSignUp.isClickable = false
            signUp()
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
                reference.child(auth.currentUser!!.uid)
                    .child(SplashFragment.FIREBASE_CHILD_USER_DATA).setValue(
                        FirebaseUser(
                            uid = auth.currentUser!!.uid,
                            username = username,
                            email = email
                        )
                    )
                findNavController().navigate(
                    SignUpFragmentDirections.actionSingUpFragmentToBottomNavigationView()
                )
            } else {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
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

data class FirebaseUser(
    val uid: String? = "",
    val username: String? = "",
    val email: String? = ""
)