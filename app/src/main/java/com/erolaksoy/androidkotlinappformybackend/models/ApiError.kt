package com.erolaksoy.androidkotlinappformybackend.models

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("status")
    var status : Int,
    @SerializedName("isShow")
    var isShow:Boolean,
    @SerializedName("errors")
    var errors:ArrayList<String>
) {
}