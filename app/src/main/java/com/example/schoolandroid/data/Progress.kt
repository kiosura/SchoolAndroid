package com.example.schoolandroid.data

data class Progress(
    val id_course: Int,
    val is_bought: Boolean,
    val whole_course: Int,
    val lessons: String,
    val status_tasks: String
)