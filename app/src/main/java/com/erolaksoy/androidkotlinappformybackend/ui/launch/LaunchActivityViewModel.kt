package com.erolaksoy.androidkotlinappformybackend.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erolaksoy.androidkotlinappformybackend.models.ApiError
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.TokenService
import com.erolaksoy.androidkotlinappformybackend.util.HelperService
import com.erolaksoy.androidkotlinappformybackend.util.IViewModelState
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState
import kotlinx.coroutines.launch

class LaunchActivityViewModel : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData()

    fun tokenCheck(): LiveData<Boolean> {
        loadingState.value = LoadingState.LOADING
        val status = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val response = TokenService.checkToken()
            status.value = response.isSuccess
            loadingState.value = LoadingState.LOADED
            if (!response.isSuccess) {
                errorState.value = response.fail
            }
        }
        return status

    }

}