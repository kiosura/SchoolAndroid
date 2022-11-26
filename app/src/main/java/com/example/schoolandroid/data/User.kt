package com.example.schoolandroid.data

data class User(
    var email: String? = null,
    var grade: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var notifications: List<Notification>? = null,
    var phone_number: String? = null,
    var progresses: Progresses? = null,
    var registered_datetime: String? = null,
    var surname: String? = null,
    var error_message: String? = null
)