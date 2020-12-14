package com.erolaksoy.androidkotlinappformybackend.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.databinding.FragmentSignInBinding
import com.erolaksoy.androidkotlinappformybackend.models.UserSignIn
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity
import com.erolaksoy.androidkotlinappformybackend.util.HelperService
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it) {
                LoadingState.LOADING -> binding.btnLogin.startAnimation()
                LoadingState.LOADED -> binding.btnLogin.revertAnimation()
            }
        })

        viewModel.errorState.observe(viewLifecycleOwner, Observer {
            HelperService.showErrorMessageWithToast(it)
        })

        binding.btnLogin.setOnClickListener {
            val userSignIn =
                UserSignIn(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
            viewModel.signIn(userSignIn).observe(viewLifecycleOwner, Observer {
                if (it) {
                    val intent = Intent(requireActivity(), UserActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            })
        }

        return binding.root
    }

}