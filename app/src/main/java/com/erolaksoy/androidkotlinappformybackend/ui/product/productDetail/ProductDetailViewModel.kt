package com.erolaksoy.androidkotlinappformybackend.ui.product.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erolaksoy.androidkotlinappformybackend.models.ApiError
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.ProductService
import com.erolaksoy.androidkotlinappformybackend.util.IViewModelState
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState
import kotlinx.coroutines.launch

class ProductDetailViewModel() : ViewModel(),IViewModelState{
    override var errorState: MutableLiveData<ApiError> = MutableLiveData()
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    fun getProductById(productId: Int): LiveData<Product> {
        var productReturn = MutableLiveData<Product>()
        loadingState.value = LoadingState.LOADING
        viewModelScope.launch {
            val response = ProductService.getProductById(productId)
            if (!response.isSuccess) errorState.value = response.fail
            productReturn.value = response.response
            loadingState.value = LoadingState.LOADED
        }
        return productReturn
    }
}