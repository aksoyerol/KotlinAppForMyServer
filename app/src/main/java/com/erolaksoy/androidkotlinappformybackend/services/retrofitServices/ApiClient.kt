package com.erolaksoy.androidkotlinappformybackend.services.retrofitServices

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        fun <T> BuildService(
            baseUrl: String,
            retrofitService: Class<T>,
            existInterceptor: Boolean
        ): T {
            val clientBuilder = OkHttpClient.Builder()
            if (existInterceptor) {
                /* TO DO: will add interceptor*/
            }
            return Retrofit.Builder().baseUrl(baseUrl).client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create()).build().create(retrofitService)
        }
    }
}