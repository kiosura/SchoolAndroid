package com.example.schoolandroid.api

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val retrofit by lazy {

        val header_name : String = "Authorization"
        val api_key : String = "PY2PDnfy.TNytIDkTIgZNyfew5dI2ucDfi6ZW1udy"

        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(
                maxIdleConnections = 5,
                keepAliveDuration = 5L,
                timeUnit = TimeUnit.MINUTES
            ))
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

