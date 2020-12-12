package com.erolaksoy.androidkotlinappformybackend.services.retrofitServices

import com.erolaksoy.androidkotlinappformybackend.services.interceptors.NetworkInterceptor
import com.erolaksoy.androidkotlinappformybackend.services.interceptors.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        fun <T> BuildService(
            baseUrl: String,
            retrofitService: Class<T>,
            existInterceptor: Boolean
        ): T {
            //loglama için interceptor eklenmesi
            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(NetworkInterceptor())

            if(existInterceptor) {
                clientBuilder.addInterceptor(TokenInterceptor())
            }
            return Retrofit.Builder().baseUrl(baseUrl).client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create()).build().create(retrofitService)
        }
    }
}