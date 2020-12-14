package com.erolaksoy.androidkotlinappformybackend.ui.launch

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.ui.login.LoginActivity
import com.erolaksoy.androidkotlinappformybackend.ui.user.UserActivity
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState

class LaunchActivity : AppCompatActivity() {
    private val viewModel: LaunchActivityViewModel by lazy {
        ViewModelProvider(this).get(LaunchActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        val loadingImage = findViewById<ImageView>(R.id.loading_image)
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(loadingImage, scaleX, scaleY)
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.repeatCount = Animation.INFINITE
        animator.duration = 1000

        viewModel.loadingState.observe(this, Observer {
            when(it){
                LoadingState.LOADING -> animator.start()
                LoadingState.LOADED -> animator.cancel()
            }
        })


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
            this@LaunchActivity.finish()
        })
    }
}