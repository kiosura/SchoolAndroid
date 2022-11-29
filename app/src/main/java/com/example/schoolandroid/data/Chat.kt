package com.example.schoolandroid.data

data class Chat (
    var name : String,
    var url : String?,
)

class Chats : ArrayList<Chat>()