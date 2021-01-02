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

class ProductDetailViewModel() : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData()

    private var _products = MutableLiveData<ArrayList<Product>>()
    val products: LiveData<ArrayList<Product>> get() = _products

    fun getProducts(page: Int) {
        if (page == 0) loadingState.value = LoadingState.LOADING
        viewModelScope.launch {
            val response = ProductService.getAllProducts(page)
            if (!response.isSuccess) errorState.value = response.fail
            _products.value = response.response

            if (page == 0) loadingState.value = LoadingState.LOADED
        }
    }
}