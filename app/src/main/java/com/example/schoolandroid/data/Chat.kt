package com.example.schoolandroid.data

data class Chat (
    val name : String,
    val link : String,
)

class Chats : ArrayList<Chat>()