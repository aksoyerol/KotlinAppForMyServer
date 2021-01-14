package com.erolaksoy.androidkotlinappformybackend.ui.product.productUpdate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erolaksoy.androidkotlinappformybackend.models.ApiError
import com.erolaksoy.androidkotlinappformybackend.util.IViewModelState
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState

class ProductUpdateViewModel : ViewModel(), IViewModelState {
    override var errorState: MutableLiveData<ApiError> = MutableLiveData()
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()

}