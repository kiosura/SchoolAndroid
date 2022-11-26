package com.example.schoolandroid.data

data class ProgressItem (
    val id : Int?,
    val id_course: Int?,
    val is_bought: Boolean?,
    val whole_course: Int?,
    val lessons: String?,
    val status_tasks: String?
) {
    // надо дописать
    fun getTaskProgress(lessonIndex : Int, taskIndex : Int) : Boolean{
        return false
    }
}

class Progresses : ArrayList<ProgressItem>() {
    fun findProgress(course_id : Int) : ProgressItem? {
        for (i in 0 until this.size)
            if (this[i].id_course == course_id) return this[i]
        return null
    }
}
