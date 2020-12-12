package com.erolaksoy.androidkotlinappformybackend.ui.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.ui.login.LoginActivity
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity

class LaunchActivity : AppCompatActivity() {
    private val viewModel: LaunchActivityViewModel by lazy {
        ViewModelProvider(this).get(LaunchActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        viewModel.tokenCheck().observe(this, Observer {
            var intent: Intent? = null
            intent = when (it) {
                true -> {
                    Intent(this, UserActivity::class.java)
                }
                false -> {
                    Intent(this, LoginActivity::class.java)
                }
            }
            startActivity(intent)
        })
    }
}