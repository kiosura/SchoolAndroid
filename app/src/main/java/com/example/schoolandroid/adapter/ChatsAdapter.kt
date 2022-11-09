package com.example.schoolandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Chat
import com.example.schoolandroid.databinding.ChatCardViewBinding

class ChatsAdapter  : RecyclerView.Adapter<ChatsAdapter.ChatHolder>() {
    private val chats = ArrayList<Chat>()

    class ChatHolder(card : View) : RecyclerView.ViewHolder(card){
        val binding = ChatCardViewBinding.bind(card)
        fun bind(chat: Chat) = with(binding){
            chatBody.text = chat.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_card_view, parent, false)
        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun addChat(chat: Chat){
        chats.add(chat)
        notifyDataSetChanged()
    }
    fun addChat(chat : List<Chat>){
        chats.addAll(chat)
        notifyDataSetChanged()
    }
}