package com.erolaksoy.androidkotlinappformybackend.services.apiServices

import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.models.ODataModel
import com.erolaksoy.androidkotlinappformybackend.models.Product
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.ApiClient
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.RetrofitProductService
import com.erolaksoy.androidkotlinappformybackend.util.HelperService

class ProductService {
    companion object {
        private val retrofitService =
            ApiClient.BuildService(ApiConsts.apiBaseUrl, RetrofitProductService::class.java, true)

        suspend fun getAllProducts(page: Int): ApiResponse<ArrayList<Product>> {
            try {
                val response = retrofitService.getAllProducts(page)
                if (!response.isSuccessful) return HelperService.handleApiError(response)
                val oDataProduct = response.body() as ODataModel<Product>
                return ApiResponse(true, oDataProduct.value)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }
        }

        suspend fun getProductById(productId: Int): ApiResponse<Product> {
            try {
                val response = retrofitService.getProduct(productId)
                if (!response.isSuccessful) return HelperService.handleApiError(response)
                val oDataProduct = response.body() as ODataModel<Product>
                return ApiResponse(true, oDataProduct.value[0])
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }
        }

        suspend fun addProduct(product: Product): ApiResponse<Product> {
            try {
                val response = retrofitService.addProduct(product)
                if (!response.isSuccessful) return HelperService.handleApiError(response)
                val responseProduct = response.body() as Product
                return ApiResponse(true, responseProduct)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }
        }

        suspend fun updateProduct(product: Product): ApiResponse<Unit> {
            try {
                val response = retrofitService.updateProduct(product.Id, product)
                if (!response.isSuccessful) return HelperService.handleApiError(response)
                return ApiResponse(true)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }

        suspend fun deleteProduct(productId: Int) : ApiResponse<Unit>{
            try{
                val response = retrofitService.deleteProduct(productId)
                if(!response.isSuccessful) return HelperService.handleApiError(response)
                return ApiResponse(true)
            }catch (ex:Exception){
                return HelperService.handleException(ex)
            }
        }


    }

}