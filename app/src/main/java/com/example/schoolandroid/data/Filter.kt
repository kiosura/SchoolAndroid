package com.example.schoolandroid.data

import com.example.schoolandroid.storage.Storage

data class Filter (
    val text : String
)

class Filters : ArrayList<Filter>()

interface FilterCompose {
    companion object {
        fun parseTags(): Filters {
            val filters = Filters()
            val str = ArrayList<String>()
            val courses = Storage.getCourses().value
            val my_courses = Storage.getCourses(isMy = true).value

            fun getTag(tags: Tags?) {
                if (tags != null) for (k in 0 until tags.size) {
                    if (!str.contains(tags[k].name)) filters.add(Filter(text = tags[k].name))
                }
            }

            if (courses != null) for (i in 0 until courses.size) {
                getTag(courses[i].tags)
            }
            if (my_courses != null) for (i in 0 until my_courses.size) {
                getTag(my_courses[i].tags)
            }

            return filters
        }
    }
}