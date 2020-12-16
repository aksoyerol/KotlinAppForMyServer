package com.erolaksoy.androidkotlinappformybackend.services.retrofitServices

import com.erolaksoy.androidkotlinappformybackend.models.Photo
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitPhotoService {

    @Multipart
    @POST("/api/photos")
    suspend fun upload(@Part photo:MultipartBody.Part): Response<Photo>

    //fotoğraf silme işlemlerinde retrofitin delete annotation'ı çalışmayabiliyor. Normal http methodu kullanıyoruz.
    @HTTP(method = "DELETE",path = "/api/photos",hasBody = true)
    suspend fun deletePhoto(@Body photo:Photo):Response<Unit>
}