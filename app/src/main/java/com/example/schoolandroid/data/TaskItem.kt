package com.example.schoolandroid.data

data class TaskItem(
    val id : Int? = null,
    var name : String? = null,
    var text : String? = null,
    var index : Int? = null,
    var drawable : Int,
    var files : Files? = null
)

class Tasks : ArrayList<TaskItem>()
