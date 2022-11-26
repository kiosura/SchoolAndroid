package com.example.schoolandroid.data

data class ProgressItem (
    val id : Int?,
    val id_course: Int?,
    var is_bought: Boolean?,
    var whole_course: Int?,
    var lessons: String?,
    var status_tasks: String?,
    var passed_tasks: String?
) {

    fun getTaskProgress(lessonIndex : Int, taskIndex : Int) : Pair<String?, Int> {
        var answer : String? = null
        val findedAnswer = parseToList(this.passed_tasks!!).get(lessonIndex).get(taskIndex)
        if (findedAnswer != "null") answer = findedAnswer

        val statusCode : Int = parseToList(this.status_tasks!!).get(lessonIndex).get(taskIndex).toInt()

        return Pair<String?, Int>(answer, statusCode)
    }

    // from models
    private fun parseToList(variable : String) : List<List<String>> {
        val array = variable.split(".")
        val finishArray = ArrayList<List<String>>()
        for (i in array.indices) {
            finishArray.add(array[i].split(" "))
        }
        return finishArray
    }

}

class Progresses : ArrayList<ProgressItem>() {
    fun findProgress(course_id : Int) : ProgressItem? {
        for (i in 0 until this.size)
            if (this[i].id_course == course_id) return this[i]
        return null
    }
}
