package com.example.schoolandroid.data

import com.google.gson.annotations.SerializedName

data class RetrofitPostRequest(
    @SerializedName("login")
    val login : String,
    @SerializedName("password")
    val password : String
)
