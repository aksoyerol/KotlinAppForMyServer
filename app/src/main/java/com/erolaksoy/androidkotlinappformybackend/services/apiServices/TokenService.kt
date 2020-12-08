package com.erolaksoy.androidkotlinappformybackend.services.apiServices

import android.content.Context
import com.erolaksoy.androidkotlinappformybackend.BuildConfig
import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.models.Introspect
import com.erolaksoy.androidkotlinappformybackend.models.Token
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.ApiClient
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.RetrofitTokenService
import com.erolaksoy.androidkotlinappformybackend.util.GlobalApp
import com.google.gson.Gson
import okhttp3.Credentials

class TokenService {
    companion object {
        private var retrofitTokenService =
            ApiClient.BuildService(ApiConsts.authBaseUrl, RetrofitTokenService::class.java, false)

        suspend fun getTokenWithClientCredential(): ApiResponse<Token> {
            var response = retrofitTokenService.getTokenWithClientCredentials(
                BuildConfig.ClientId_CC,
                BuildConfig.ClientSecrets_CC,
                ApiConsts.clientCredentialsGrantType
            )
            //TODO başarısız olan metodlar için errorhandler yaz.
            if (!response.isSuccessful) return ApiResponse(false)
            return ApiResponse(true, response.body() as Token)
        }

        fun refreshToken(refreshToken: String): ApiResponse<Token> {
            val response = retrofitTokenService.refreshToken(
                BuildConfig.ClientId_ROP,
                BuildConfig.ClientSecrets_ROP,
                ApiConsts.resourceOwnerPasswordCredentialGrantType,
                refreshToken
            ).execute()

            if (!response.isSuccessful) return ApiResponse(false)
            return ApiResponse(true, response.body() as Token)

        }

        suspend fun checkToken(): ApiResponse<Unit> {
            val preferences =
                GlobalApp.getAppContext().getSharedPreferences("ApiToken", Context.MODE_PRIVATE)

            val tokenString: String =
                preferences.getString("token", null) ?: return ApiResponse(false)

            val token: Token = Gson().fromJson(tokenString, Token::class.java)

            //kontrol edilecek
            val authorization: String = Credentials.basic("resource_product_api", "apisecret")
            val response = retrofitTokenService.checkToken(token.AccessToken, authorization)

            if (!response.isSuccessful) return ApiResponse(false)
            val introspect = response.body() as Introspect
            if (introspect.active) return ApiResponse(false)

            return ApiResponse(true)
        }

    }
}