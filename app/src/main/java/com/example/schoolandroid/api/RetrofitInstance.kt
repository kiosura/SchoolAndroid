package com.example.schoolandroid.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {

        val header_name : String = "Authorization"
        val api_key : String = "PY2PDnfy.TNytIDkTIgZNyfew5dI2ucDfi6ZW1udy"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{chain ->
                val request = chain.request().newBuilder()
                    .addHeader(header_name, api_key)
                    .addHeader("Content-Type", "application/json")
                    .build()

                return@addInterceptor chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl("https://samotokhin.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    val api: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }

}

