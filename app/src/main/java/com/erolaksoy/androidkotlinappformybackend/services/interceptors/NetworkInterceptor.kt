package com.erolaksoy.androidkotlinappformybackend.services.interceptors

import com.erolaksoy.androidkotlinappformybackend.util.LiveNetworkListener
import com.erolaksoy.androidkotlinappformybackend.util.OfflineException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    //her requestte devreye girerek connection'ı kontrol edecek.
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!LiveNetworkListener.isConnected()) {
            throw OfflineException("")
        }
        val request = chain.request()
        return chain.proceed(request)
    }
}