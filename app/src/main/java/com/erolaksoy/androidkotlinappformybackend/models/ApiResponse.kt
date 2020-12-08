package com.erolaksoy.androidkotlinappformybackend.models

data class ApiResponse<T>(var isSuccess:Boolean,var response: T?=null,var fail:ApiError?=null) {}
