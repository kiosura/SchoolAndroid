package com.example.schoolandroid.data

import com.example.schoolandroid.storage.Storage
import com.google.gson.annotations.SerializedName

data class CourseItem(
    val date_open: String,
    val duration: String,
    val id: Int,
    val lessonsCount: Int,
    var lessons: Lessons,
    val name: String,
    val product_preview: String,
    val description: String,
    val teachers: Teachers,
    @SerializedName("chat") var chats: Chats,
    val value: Int,
    var tags: Tags?
)

class Courses : ArrayList<CourseItem>()

sealed interface CourseCompose {
    companion object {
        fun getWithTag(filter: Filter, isMy: Boolean) : Courses {
            val courses = Courses()
            val data = Storage.getCourses(isMy = isMy).value

            fun getNames(tags: Tags?) : Boolean {
                if (tags != null) for (k in 0 until tags.size) {
                    if (filter.text == tags[k].name) return true
                }
                return false
            }

            if (data != null) for (i in 0 until data.size) {
                if (getNames(data[i].tags)) courses.add(data[i])
            }

            return courses
        }
    }
}