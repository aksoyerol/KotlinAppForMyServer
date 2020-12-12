package com.erolaksoy.androidkotlinappformybackend.services.interceptors

import android.content.Context
import android.util.Log
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.models.Token
import com.erolaksoy.androidkotlinappformybackend.services.apiServices.TokenService
import com.erolaksoy.androidkotlinappformybackend.util.GlobalApp
import com.erolaksoy.androidkotlinappformybackend.util.HelperService
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var token: Token? = null
        var request = chain.request()
        val preferences =
            GlobalApp.getAppContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)
        val tokenString = preferences.getString("token", null)
        if (tokenString != null) {
            Log.i("OkHttp", "tokenString is not null!")
            token = Gson().fromJson(tokenString, Token::class.java)
            request = request.newBuilder().addHeader("Authorization", "Bearer ${token.AccessToken}")
                .build()
        }
        var response = chain.proceed(request)

        if (response.code == 401) {
            //401'e takılırse, sharedPreferences'te kayıtlı tokenımız varsa refresh token ile yeni token alacağız.
            Log.i("OkHttp", "401 Authorization Error")
            if (token != null) {
                val apiResponse = TokenService.refreshToken(token.RefreshToken)
                if (apiResponse.isSuccess) {
                    HelperService.saveTokenSharedPreferences(apiResponse.response!!)
                    //yeni token ile yeniden istek
                    var newToken = apiResponse.response
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer ${newToken!!.AccessToken}").build()
                    response = chain.proceed(request)
                }
            }
        }
        return response
    }
}