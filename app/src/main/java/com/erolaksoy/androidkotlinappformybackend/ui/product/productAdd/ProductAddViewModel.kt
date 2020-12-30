package com.erolaksoy.androidkotlinappformybackend.ui.product.productAdd

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erolaksoy.androidkotlinappformybackend.models.ApiError
import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.CategoryService
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.PhotoService
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.ProductService
import com.erolaksoy.androidkotlinappformybackend.util.IViewModelState
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState
import kotlinx.coroutines.launch

class ProductAddViewModel() : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData()

    fun getCategories(): LiveData<ArrayList<Category>> {
        loadingState.value = LoadingState.LOADING
        val categoryReturn = MutableLiveData<ArrayList<Category>>()
        viewModelScope.launch {
            val response = CategoryService.getCategoryList()
            if (!response.isSuccess) errorState.value = response.fail
            categoryReturn.value = response.response
            loadingState.value = LoadingState.LOADED
        }

        return categoryReturn
    }

    fun addProduct(product: Product, fileUri: Uri?): LiveData<Product> {
        loadingState.value = LoadingState.LOADING
        val productReturn = MutableLiveData<Product>()
        viewModelScope.launch {
            if (fileUri != null) {
                val photoResponse = PhotoService.uploadPhoto(fileUri)
                if (!photoResponse.isSuccess) errorState.value = photoResponse.fail
                product.PhotoPath = photoResponse.response!!.Url
            }

            val productResponse = ProductService.addProduct(product)
            if (!productResponse.isSuccess) errorState.value = productResponse.fail
            productReturn.value = productResponse.response
            loadingState.value = LoadingState.LOADED
        }

        return productReturn
    }
}