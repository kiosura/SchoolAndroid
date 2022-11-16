package com.example.schoolandroid.api
import com.example.schoolandroid.data.CourseX
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    @GET ("course")
    @Headers("Content-Type: application/json")
    suspend fun fetchCourses(): Response<CourseX>
}