package com.example.schoolandroid.data

import com.google.gson.annotations.SerializedName

data class RetrofitPostRequest(
    @SerializedName("login")
    val login : String,
    @SerializedName("password")
    val password : String
)

data class RetrofitLessonRequest(
    @SerializedName("course_id")
    val course_id : Int,
    @SerializedName("lesson_index")
    val lesson_index : Int
)

data class RetrofitTaskRequest(
    @SerializedName("course_id")
    val course_id : Int,
    @SerializedName("lesson_index")
    val lesson_index : Int,
    @SerializedName("task_index")
    val task_index : Int
)
