package com.example.schoolandroid.api

import com.example.schoolandroid.data.*
import retrofit2.Response
import retrofit2.http.*


interface RetrofitApi {

//  self
    @POST ("course/chats/self")
    @Headers("Content-Type: application/json")
    suspend fun fetchMyChats(@Body data : RetrofitUserIdPostRequest) : Response<Courses>

    @POST ("course/self")
    @Headers("Content-Type: application/json")
    suspend fun fetchMyCourses(@Body data : RetrofitUserIdPostRequest) : Response<Courses>

//  default

    @GET ("course")
    @Headers("Content-Type: application/json")
    suspend fun fetchCourses() : Response<Courses>

    @POST ("course")
    @Headers("Content-Type: application/json")
    suspend fun fetchCoursesPost(@Body data : RetrofitUserIdPostRequest) : Response<Courses>

    @GET ("course/lessons")
    @Headers("Content-Type: application/json")
    suspend fun fetchLessons() : Response<Courses>

    @GET ("course/chats")
    @Headers("Content-Type: application/json")
    suspend fun fetchChats() : Response<Courses>

    @POST ("course/chats")
    @Headers("Content-Type: application/json")
    suspend fun fetchChatsPost(@Body data : RetrofitUserIdPostRequest) : Response<Courses>

    @POST ("course/lessons")
    @Headers("Content-Type: application/json")
    suspend fun fetchLessonsPost(@Body data : RetrofitUserIdPostRequest) : Response<Courses>

    @POST("point/course/lesson")
    @Headers("Content-Type: application/json")
    suspend fun fetchLesson(@Body data : RetrofitLessonRequest): Response<LessonItem>

    @POST("task/answer")
    @Headers("Content-Type: application/json")
    suspend fun fetchTaskAnswer(@Body data : RetrofitTaskAnswerRequest): Response<User>

//    @POST("point/course/lesson/task")
//    @Headers("Content-Type: application/json")
//    suspend fun getTask():Response<>

    @POST("user/reg")
    @Headers("Content-Type: application/json")
    suspend fun sendRegistration(@Body data : RetrofitUserPostRequest) : Response<User>

    @POST("user/auth")
    @Headers("Content-Type: application/json")
    suspend fun authentication(@Body data : RetrofitUserPostRequest) : Response<User>

    @POST("user/progresses")
    @Headers("Content-Type: application/json")
    suspend fun userProgresses(@Body data : RetrofitUserIdPostRequest) : Response<User>


//    @POST("user/update")
//    @Headers("Content-Type: application/json")
//    suspend fun ():Response<>



}