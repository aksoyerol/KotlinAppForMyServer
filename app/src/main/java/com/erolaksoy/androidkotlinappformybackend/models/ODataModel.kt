package com.erolaksoy.androidkotlinappformybackend.models

import com.google.gson.annotations.SerializedName

data class ODataModel<T>(
    @SerializedName("value")
    var value : ArrayList<T>
)
