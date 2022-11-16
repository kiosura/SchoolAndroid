package com.example.schoolandroid.data

data class CourseItem(
    val date_open: String,
    val duration: String,
    val id: Int,
    val lessons: List<LessonX>,
    val lessonsCount: Int,
    val name: String,
    val product_preview: String,
    val teachers: List<Teacher>,
    val value: Int
)