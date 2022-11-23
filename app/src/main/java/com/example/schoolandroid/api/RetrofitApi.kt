package com.example.schoolandroid.api

import androidx.lifecycle.MutableLiveData
import com.example.schoolandroid.data.*
import retrofit2.Response
import retrofit2.http.*


interface RetrofitApi {

    @GET ("course")
    @Headers("Content-Type: application/json")
    suspend fun fetchCourses() : Response<Courses>

    @GET ("course/lessons")
    @Headers("Content-Type: application/json")
    suspend fun fetchLessons() : Response<Courses>

    @POST("point/course/lesson")
    @Headers("Content-Type: application/json")
    suspend fun fetchLesson(@Body data : RetrofitLessonRequest): Response<LessonItem>

//    @POST("point/course/lesson/task")
//    @Headers("Content-Type: application/json")
//    suspend fun getTask():Response<>

    @POST("user/reg")
    @Headers("Content-Type: application/json")
    suspend fun sendRegistration(@Body data : RetrofitPostRequest)

    @POST("user/auth")
    @Headers("Content-Type: application/json")
    suspend fun authentication() : Response<User>


//    @POST("user/update")
//    @Headers("Content-Type: application/json")
//    suspend fun ():Response<>



}