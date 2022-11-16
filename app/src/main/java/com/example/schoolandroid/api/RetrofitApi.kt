package com.example.schoolandroid.api
import com.example.schoolandroid.data.Course
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    @GET ("/course")
    @Headers("Content-Type: application/json")
    suspend fun fetchCourses(): Response<Course>

    @POST("/sendPhoto")
    @Multipart
    suspend fun sendTask(@Part body: MultipartBody.Part)

}