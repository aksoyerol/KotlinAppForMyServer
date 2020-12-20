package com.erolaksoy.androidkotlinappformybackend.services.apiServices

import android.net.Uri
import com.erolaksoy.androidkotlinappformybackend.consts.ApiConsts
import com.erolaksoy.androidkotlinappformybackend.models.ApiResponse
import com.erolaksoy.androidkotlinappformybackend.models.Photo
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.ApiClient
import com.erolaksoy.androidkotlinappformybackend.services.retrofitServices.RetrofitPhotoService
import com.erolaksoy.androidkotlinappformybackend.util.GlobalApp
import com.erolaksoy.androidkotlinappformybackend.util.HelperService
import com.erolaksoy.androidkotlinappformybackend.util.RealPathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PhotoService {
    companion object{
        private var retrofitService = ApiClient.BuildService(ApiConsts.photoApiBaseUrl,RetrofitPhotoService::class.java,true)

        suspend fun uploadPhoto(fileUri : Uri) :ApiResponse<Photo>{
            try {
                val realPath = RealPathUtil.getRealPath(GlobalApp.getAppContext(),fileUri)
                val file = File(realPath)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("photo",file.name,requestFile)
                val response = retrofitService.upload(body)
                if(!response.isSuccessful) return HelperService.handleApiError(response)
                return ApiResponse(true,response.body() as Photo)
            } catch (ex: Exception) {
                return HelperService.handleException(ex)
            }
        }

        suspend fun deletePhoto(photo:Photo) : ApiResponse<Unit>{
            try{
                val response = retrofitService.deletePhoto(photo)
                if(!response.isSuccessful) return HelperService.handleApiError(response)
                return ApiResponse(true)
            }catch (ex:Exception){
                return HelperService.handleException(ex)
            }
        }
    }
}