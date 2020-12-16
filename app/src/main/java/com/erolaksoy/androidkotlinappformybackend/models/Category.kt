package com.erolaksoy.androidkotlinappformybackend.models

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("Id")
    var Id: Int,
    @SerializedName("Name")
    var Name: String
) {}
