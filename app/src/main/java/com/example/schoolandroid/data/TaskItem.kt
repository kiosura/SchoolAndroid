package com.example.schoolandroid.data

data class TaskItem(
    val id : Int,
    val text : String,
    val color : Int
)

class Tasks : ArrayList<TaskItem>()
