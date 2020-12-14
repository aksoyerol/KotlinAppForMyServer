package com.erolaksoy.androidkotlinappformybackend.services.apiServices

import com.erolaksoy.androidkotlinappformybackend.BuildConfig
import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.models.Token
import com.erolaksoy.androidkotlinappformybackend.models.UserSignIn
import com.erolaksoy.androidkotlinappformybackend.models.UserSignUp
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.ApiClient
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.RetrofitLoginService
import com.erolaksoy.androidkotlinappformybackend.util.HelperService

class LoginService {
    companion object {
        //without interceptor
        private val retrofitTokenService =
            ApiClient.BuildService(ApiConsts.authBaseUrl, RetrofitLoginService::class.java, false)

        suspend fun signUp(userSignUp: UserSignUp): ApiResponse<Unit> {
            try {
                val tokenResponse = TokenService.getTokenWithClientCredential()
                if (!tokenResponse.isSuccess) return ApiResponse(false,fail = tokenResponse.fail)
                val token = tokenResponse.response!!.AccessToken
                val response =
                    retrofitTokenService.signUp(userSignUp, "Bearer $token")

                if (!response.isSuccessful) return HelperService.handleApiError(response)
                return ApiResponse(true)

            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }
        }

        suspend fun signIn(userSignIn: UserSignIn): ApiResponse<Unit> {
            try {
                val response = retrofitTokenService.signIn(
                    BuildConfig.ClientId_ROP,
                    BuildConfig.ClientSecrets_ROP,
                    ApiConsts.resourceOwnerPasswordCredentialGrantType,
                    userSignIn.Email,
                    userSignIn.Password
                )
                //if(!response.isSuccessful) return ApiResponse(false) şimdi bu kodun hata kontrolünü helperService'mizle yapacağız.
                if (!response.isSuccessful) return HelperService.handleApiError(response)

                //kullanıcı bilgileriyle girdik. connect/token endpointinden gelen Kullanıcıya özgü  token
                val token = response.body() as Token

                //TODO şimdi ise tokenı sharedPreferences'e kaydetmemiz gerekiyor.
                //Bu arada helperService olarak saveTokenSharedPreferences'i yazdık.
                HelperService.saveTokenSharedPreferences(token)
                return ApiResponse(true)

            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }

        }

    }
}