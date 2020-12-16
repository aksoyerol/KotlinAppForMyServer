package com.erolaksoy.androidkotlinappformybackend.services.retrofitServices

import com.erolaksoy.androidkotlinappformybackend.models.Category
import com.erolaksoy.androidkotlinappformybackend.models.ODataModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitCategoryService {
    @GET("/odata/categories")
    suspend fun getCategories():Response<ODataModel<Category>>
}