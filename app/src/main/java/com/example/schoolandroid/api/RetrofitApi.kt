package com.example.schoolandroid.api

import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.Lessons
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    @GET ("course")
    @Headers("Content-Type: application/json")
    suspend fun fetchCourses(): Response<Courses>

    @GET ("course/lessons")
    @Headers("Content-Type: application/json")
    suspend fun fetchLessons(): Response<Courses>

    @POST("sendPhoto")
    @Multipart
    suspend fun sendTask(@Part body: MultipartBody.Part)

}