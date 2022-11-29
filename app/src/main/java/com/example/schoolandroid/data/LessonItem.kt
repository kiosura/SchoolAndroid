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
) {
    fun getAccess() : Boolean {
        when (access) {
            "0" -> return true
            else -> return false
        }
    }
}

class Lessons : ArrayList<LessonItem>()