package com.example.schoolandroid.data

data class Chat (
    var name : String,
    var url : String?,
    var isPersonally : Boolean = false
)
{
    companion object {
        fun teachers_chat_creation(name: String, surname: String, url: String? = null): Chat {
            val generated_name = "${name} ${surname}"
            return Chat(name=generated_name, url=url, isPersonally = true)
        }
    }
}

class Chats : ArrayList<Chat>()