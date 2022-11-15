package com.example.schoolandroid.api

import android.database.Observable
import com.example.schoolandroid.data.Course
import com.example.schoolandroid.data.CourseX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await

interface FirstApi {

    companion object {
        private const val header_name : String = "Authorization"
        private const val api_key : String = "BF7nDuG4.ZTGSsdUsnqSIk9ab2psqtEKrx19Pfkyb"
        private val client : OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(header_name, api_key)
                    .build()

                return@addInterceptor chain.proceed(request)
            }
            .build()
        private const val url : String = "https://kofefast.ru/api/course/"
        private const val api_url : String = "http://127.0.0.1:8000/api/course/"

        suspend fun apiBase(): String{
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).await()
            val typeCourseX : ArrayList<CourseX> = object : ArrayList<CourseX>() {}
            val gson : ArrayList<CourseX> = Gson().fromJson<ArrayList<CourseX>>(response.body()!!.string(), typeCourseX::class.java)
            return gson[1].name
        }
    }
}
