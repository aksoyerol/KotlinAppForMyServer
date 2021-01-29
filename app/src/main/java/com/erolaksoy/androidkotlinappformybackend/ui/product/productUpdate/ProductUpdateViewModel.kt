package com.erolaksoy.androidkotlinappformybackend.ui.product.productUpdate

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erolaksoy.androidkotlinappformybackend.models.ApiError
import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.models.Photo
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.CategoryService
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.PhotoService
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.ProductService
import com.erolaksoy.androidkotlinappformybackend.util.IViewModelState
import com.erolaksoy.androidkotlinappformybackend.util.LoadingState
import kotlinx.coroutines.launch

class ProductUpdateViewModel : ViewModel(), IViewModelState {
    override var errorState: MutableLiveData<ApiError> = MutableLiveData()
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    fun getCategoryList(): LiveData<ArrayList<Category>> {
        loadingState.value = LoadingState.LOADING
        val returnCategory = MutableLiveData<ArrayList<Category>>()
        viewModelScope.launch {
            val response = CategoryService.getCategoryList()
            if (!response.isSuccess) errorState.value = response.fail
            returnCategory.value = response.response
            loadingState.value = LoadingState.LOADED
        }
        return returnCategory
    }

    fun updateProduct(product: Product, fileUri: Uri?): LiveData<Boolean> {
        loadingState.value = LoadingState.LOADING
        val returnData = MutableLiveData<Boolean>()
        viewModelScope.launch {
            if (fileUri != null) {
                if (product.PhotoPath.isNotEmpty()) {
                    PhotoService.deletePhoto(Photo(product.PhotoPath))
                }
                val response = PhotoService.uploadPhoto(fileUri)
                if (response.isSuccess) {
                    product.PhotoPath = response.response!!.Url

                } else {
                    errorState.value = response.fail
                }
            }

            val apiResponse = ProductService.updateProduct(product)
            if (!apiResponse.isSuccess) {
                errorState.value = apiResponse.fail
                returnData.value = false
            } else {
                returnData.value = true
            }
            loadingState.value = LoadingState.LOADED
        }
        return returnData
    }

}