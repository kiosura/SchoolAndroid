package com.example.schoolandroid.data

import com.google.gson.annotations.SerializedName

data class RetrofitUserPostRequest(
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

data class RetrofitTaskAnswerRequest(
    @SerializedName("course_id")
    val course_id : Int,
    @SerializedName("lesson_index")
    val lesson_index : Int,
    @SerializedName("task_index")
    val task_index : Int,
    @SerializedName("user_id")
    val user_id : Int,
    @SerializedName("answer")
    val answer : String,
)

data class RetrofitUserIdPostRequest(
    @SerializedName("user_id")
    val user_id : Int
)

data class RetrofitUpdateUserPostRequest(
    @SerializedName("user_id")
    val user_id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("surname")
    val surname : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("phone_nember")
    val phone_number : String
)
