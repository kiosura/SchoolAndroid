package com.example.schoolandroid.data

import java.time.LocalDate


data class Notification(
    val created: LocalDate,
    val content: String,
    val type: TypesNotification,
)
