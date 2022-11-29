package com.example.schoolandroid.data

data class Chat (
    val name : String,
    val url : String,
)

class Chats : ArrayList<Chat>()