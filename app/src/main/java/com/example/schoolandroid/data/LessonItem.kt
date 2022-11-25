package com.example.schoolandroid.data

data class LessonItem(
    val id: Int,
    var name: String,
    var access: String,
    var getTasks : Int,
    var index : Int,
    var description : String,
    
)

class Lessons : ArrayList<LessonItem>()