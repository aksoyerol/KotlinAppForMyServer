package com.erolaksoy.androidkotlinappformybackend.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.erolaksoy.androidkotlinappformybackend.R
import com.google.android.material.tabs.TabLayoutMediator

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        //val navController = findNavController(R.id.userNavHostFragment)

    }
}