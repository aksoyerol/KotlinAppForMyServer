package com.erolaksoy.androidkotlinappformybackend.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Product(
    @SerializedName("Id")
    var Id: Int,
    @SerializedName("Name")
    var Name: String,
    @SerializedName("Price")
    var Price: Double,
    @SerializedName("Color")
    var Color: String,
    @SerializedName("Stock")
    var Stock: Int,
    @SerializedName("PhotoPath")
    var PhotoPath: String,
    @SerializedName("CategoryId")
    var CategoryId: Int,
    @SerializedName("Category")
    var Category: Category?
) : Parcelable