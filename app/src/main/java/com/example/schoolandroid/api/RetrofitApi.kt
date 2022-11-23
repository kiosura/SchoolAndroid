package com.example.schoolandroid.api

import androidx.lifecycle.MutableLiveData
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.User
import retrofit2.Response
import retrofit2.http.*
import com.example.schoolandroid.data.RetrofitPostRequest


interface RetrofitApi {

    @GET ("course")
    @Headers("Content-Type: application/json")
    suspend fun fetchCourses() : Response<Courses>

//    @POST("point/course/lesson")
//    @Headers("Content-Type: application/json")
//    suspend fun getLesson():Response<>

    @GET ("course/lessons")
    @Headers("Content-Type: application/json")
    suspend fun fetchLessons() : Response<Courses>

//    @POST("point/course/lesson/task")
//    @Headers("Content-Type: application/json")
//    suspend fun getTask():Response<>

    @POST("user/reg")
    @Headers("Content-Type: application/json")
    suspend fun sendRegistration(@Body data : Map<String, String>)

    @POST("user/auth")
    @Headers("Content-Type: application/json")
    suspend fun authentication() : Response<User>


//    @POST("user/update")
//    @Headers("Content-Type: application/json")
//    suspend fun ():Response<>



}