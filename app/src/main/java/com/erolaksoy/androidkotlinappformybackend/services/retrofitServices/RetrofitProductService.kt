package com.erolaksoy.androidkotlinappformybackend.services.retrofitServices

import com.erolaksoy.androidkotlinappformybackend.models.ODataModel
import com.erolaksoy.androidkotlinappformybackend.models.Product
import retrofit2.Response
import retrofit2.http.*

interface RetrofitProductService {

    @GET("/odata/products?\$expand=category(\$select=Name)&\$select=id,name,stock,price,photoPath&\$orderby=id desc")
    suspend fun getAllProducts(@Query("\$skip") page:Int):Response<ODataModel<Product>>

    @GET("/odata/products({productId})")
    suspend fun getProduct(@Path("productId") productId: Int): Response<ODataModel<Product>>

    @POST("/odata/products")
    suspend fun addProduct(@Body product: Product): Response<Product>

    @PUT("/odata/products({productId})")
    suspend fun updateProduct(@Path("productId") productId: Int,@Body product: Product):Response<Unit>

    @DELETE("/odata/products({productId})")
    suspend fun deleteProduct(@Path("productId") productId: Int):Response<Unit>



}