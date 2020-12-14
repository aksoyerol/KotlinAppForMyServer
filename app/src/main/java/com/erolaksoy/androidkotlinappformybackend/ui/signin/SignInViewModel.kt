package com.erolaksoy.androidkotlinappformybackend.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erolaksoy.androidkotlinappformybackend.models.ApiError
import com.erolaksoy.androidkotlinappformybackend.models.UserSignIn
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.LoginService
import com.erolaksoy.androidkotlinappformybackend.util.IViewModelState
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel(),IViewModelState{
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData()

    fun signIn(userSignIn: UserSignIn):LiveData<Boolean>{
        val status=MutableLiveData<Boolean>()
        loadingState.value = LoadingState.LOADING
        viewModelScope.launch{
            val response = LoginService.signIn(userSignIn)
            status.value=response.isSuccess
            loadingState.value = LoadingState.LOADED
            if(!response.isSuccess) errorState.value = response.fail
        }
        return status
    }
}