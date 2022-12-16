package com.example.schoolandroid.adapter.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Chat
import com.example.schoolandroid.data.Chats
import com.example.schoolandroid.databinding.ChatCardViewBinding
import com.example.schoolandroid.interfaces.Listener

class ChatsAdapter(val listener: Listener)  : RecyclerView.Adapter<ChatsAdapter.ChatHolder>(){
    private var chats = Chats()

    class ChatHolder(card : View) : RecyclerView.ViewHolder(card){
        private val binding = ChatCardViewBinding.bind(card)

        fun bind(chat: Chat) = with(binding){
            chatName.text = chat.name
            if (chat.isPersonally) chatAction.text = "Задать вопрос"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_card_view, parent, false)
        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bind(chats[position])
        holder.itemView.findViewById<LinearLayout>(R.id.chatBody)
            .setOnClickListener(View.OnClickListener{
                listener.onClick(position)
            })
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun getLink(position: Int) : String? {
        return chats[position].url
    }

    fun addChats(list : Chats?){
        if (list != null) {
            chats = list
            notifyDataSetChanged()
        }
    }
}