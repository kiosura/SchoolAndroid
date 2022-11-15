package com.example.schoolandroid.data

data class Teacher(
    val avatar: Any,
    val description: String,
    val id: Int,
    val is_active: Boolean,
    val last_login: String,
    val my_courses: String,
    val name: String,
    val notifications: List<Any>,
    val progresses: List<Any>,
    val registered: String,
    val surname: String
)