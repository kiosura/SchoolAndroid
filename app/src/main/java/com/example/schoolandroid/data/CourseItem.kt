package com.example.schoolandroid.data

data class CourseItem(
    val date_open: String,
    val duration: String,
    val id: Int,
    val lessonsCount: Int,
    var lessons: Lessons,
    val name: String,
    val product_preview: String,
    val teachers: List<Teacher>,
    val value: Int
)