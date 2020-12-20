package com.erolaksoy.androidkotlinappformybackend.util

import androidx.lifecycle.MutableLiveData
import com.erolaksoy.androidkotlinappformybackend.models.ApiError

interface IViewModelState {
    var loadingState: MutableLiveData<LoadingState>
    var errorState: MutableLiveData<ApiError>
}