package com.example.schoolandroid.data

data class User(
    val email: String?,
    val grade: String?,
    val id: Int?,
    val name: String?,
    val notifications: List<Notification>?,
    val phone_number: String?,
    val progresses: List<Progress>?,
    val registered: String?,
    val surname: String?
)