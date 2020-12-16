package com.erolaksoy.androidkotlinappformybackend.services.apiServices

import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.models.ODataModel
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.ApiClient
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.RetrofitCategoryService
import com.erolaksoy.androidkotlinappformybackend.util.HelperService


class CategoryService {
    companion object {
        private var retrofitService =
            ApiClient.BuildService(ApiConsts.apiBaseUrl, RetrofitCategoryService::class.java, true)

        suspend fun getCategoryList(): ApiResponse<ArrayList<Category>> {
            try {
                val response = retrofitService.getCategories()
                if (!response.isSuccessful) return HelperService.handleApiError(response)
                val oDataCategory = response.body() as ODataModel<Category>
                return ApiResponse(true, oDataCategory.value)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }
        }
    }
}