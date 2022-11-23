package com.example.schoolandroid.data

data class LessonItem(
    val id: Int,
    var name: String,
    var access: String,
    var tasksCount : Int,
    var index : Int,
    var description : String
)