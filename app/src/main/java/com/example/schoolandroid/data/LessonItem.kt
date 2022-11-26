package com.example.schoolandroid.data

data class LessonItem(
    val id: Int?,
    var name: String?,
    var access: String?,
    var getTasks : Int?,
    var index : Int?,
    var description : String?,
    var homework: Homework?,
    var link : String?
)

class Lessons : ArrayList<LessonItem>()