package com.erolaksoy.androidkotlinappformybackend.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.databinding.ActivityUserBinding
import com.erolaksoy.androidkotlinappformybackend.util.HelperService
import com.erolaksoy.androidkotlinappformybackend.util.IViewModelState
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState
import com.google.android.material.tabs.TabLayoutMediator

class UserActivity : AppCompatActivity() {

    companion object {
        private lateinit var loadingView: View
        fun setLoadingState(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {
            viewModel.loadingState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadingState.LOADING -> loadingView.visibility = View.VISIBLE
                    LoadingState.LOADED -> loadingView.visibility = View.GONE
                }
            })
        }

        fun setErrorState(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {
            viewModel.errorState.observe(viewLifecycleOwner, Observer {
                HelperService.showErrorMessageWithToast(it)
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        val binding =
//            DataBindingUtil.setContentView<ActivityUserBinding>(this, R.layout.activity_user)
        setContentView(R.layout.activity_user)
        loadingView = findViewById(R.id.fullPageLoadingView)
    }
}