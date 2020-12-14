package com.erolaksoy.androidkotlinappformybackend.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.databinding.FragmentSignUpBinding
import com.erolaksoy.androidkotlinappformybackend.models.UserSignUp
import com.erolaksoy.androidkotlinappformybackend.util.HelperService
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.LoginViewPager)

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when(it){
                LoadingState.LOADING -> binding.btnCreateAccount.startAnimation()
                LoadingState.LOADED -> binding.btnCreateAccount.revertAnimation()
            }
        })

        viewModel.errorState.observe(viewLifecycleOwner, Observer {
            HelperService.showErrorMessageWithToast(it)
        })

        binding.btnCreateAccount.setOnClickListener {
            val userSignUp = UserSignUp(
                binding.txtUsername.text.toString(),
                binding.txtPassword.text.toString(),
                binding.txtEmail.text.toString(),
                binding.txtCity.text.toString()
            )

            viewModel.signUp(userSignUp).observe(viewLifecycleOwner, Observer {
                if (it) {
                    //TODO signIn fragment'e yönlendir
                    viewPager.currentItem=0
                } else {
                    //hata
                }
            })
        }

        return binding.root
    }


}