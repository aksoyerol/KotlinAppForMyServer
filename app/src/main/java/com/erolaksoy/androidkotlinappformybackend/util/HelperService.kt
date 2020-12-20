package com.erolaksoy.androidkotlinappformybackend.util

import OfflineException
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.erolaksoy.androidkotlinappformybackend.R
import com.erolaksoy.androidkotlinappformybackend.models.ApiError
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.models.Token
import com.google.gson.Gson
import retrofit2.Response
import java.lang.StringBuilder


class HelperService {
    companion object {

        fun getTokenSharedPreferences(): Token? {
            val sharedPreferences =
                GlobalApp.getAppContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)
            val tokenString = sharedPreferences.getString("token", null)
            var token: Token? = null
            if (!tokenString.isNullOrEmpty()) {
                token = Gson().fromJson(tokenString, Token::class.java)
            }
            return token
        }

        fun saveTokenSharedPreferences(token: Token) {
            val sharedPreferences =
                GlobalApp.getAppContext().getSharedPreferences("token_api", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            //TODO hata var!! olmayada bilir. Emin olamadım. şey, bir ara dön bak
            editor.putString("token", Gson().toJson(token))
            editor.apply()
        }

        /**
         * This function, handle error on api.
         */
        fun <T1, T2> handleApiError(response: Response<T1>): ApiResponse<T2> {
            var apiError: ApiError? = null

            if (response.errorBody() != null) {
                val errorBody = response.errorBody()!!.string() //json olarak data döndü.
                apiError = Gson().fromJson(errorBody, ApiError::class.java)
                Log.i("OkHttp", apiError.toString())
            }
            return ApiResponse(false, null, apiError)
        }

        fun <T> handleException(ex: Exception): ApiResponse<T> {
            return when (ex) {
                is OfflineException -> {
                    val exMessage =
                        arrayListOf(GlobalApp.getAppContext().getString(R.string.ex_connect_failed))
                    val apiError: ApiError = ApiError(500, true, exMessage)
                    ApiResponse(false, null, apiError)
                }

                else -> {
                    val exMessage =
                        arrayListOf(GlobalApp.getAppContext().getString(R.string.ex_common_occ))
                    val apiError: ApiError = ApiError(500, true, exMessage)
                    ApiResponse(false, null, apiError)
                }
            }
        }

        fun showErrorMessageWithToast(apiError: ApiError?) {
            if (apiError == null) return
            val stringBuilder = StringBuilder()
            for (error in apiError.errors) {
                stringBuilder.append(error + "\n")
            }

            Toast.makeText(GlobalApp.getAppContext(), stringBuilder.toString(), Toast.LENGTH_LONG)
                .show()
        }

    }
}