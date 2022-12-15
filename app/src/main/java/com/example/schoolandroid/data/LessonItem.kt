package com.example.schoolandroid.data

import com.example.schoolandroid.storage.Storage

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
    // using only for CourseActivity
    fun getAccess() : Boolean {
        val bought = Storage.getUser().value?.progresses?.
                findProgress(Storage.getCurrentCourse().value!!.id)?.is_bought ?: false
        when (access) {
            "0" -> return true
            "1" -> return bought
            else -> return false
        }
    }
}

class Lessons : ArrayList<LessonItem>()