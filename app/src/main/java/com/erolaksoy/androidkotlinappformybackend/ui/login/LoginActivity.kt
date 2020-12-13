package com.erolaksoy.androidkotlinappformybackend.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.databinding.ActivityLoginBinding
import com.erolaksoy.androidkotlinappformybackend.ui.signin.SignInFragment
import com.erolaksoy.androidkotlinappformybackend.ui.signup.SignUpFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        val pagerAdapter = LoginViewPagerAdapter(this)
        pagerAdapter.apply {
            addFragment(SignInFragment())
            addFragment(SignUpFragment())
        }

        binding.LoginViewPager.adapter = pagerAdapter

    }
}