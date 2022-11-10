package com.example.schoolandroid.adapter.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.Chat
import com.example.schoolandroid.databinding.ChatCardViewBinding

class ChatsAdapter(val listener: Listener)  : RecyclerView.Adapter<ChatsAdapter.ChatHolder>(){
    private val chats = ArrayList<Chat>()

    class ChatHolder(card : View) : RecyclerView.ViewHolder(card){
        private val binding = ChatCardViewBinding.bind(card)

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
        holder.itemView.findViewById<Button>(R.id.chatBody)
            .setOnClickListener(View.OnClickListener{

                listener.onClick(position)

            })
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
    interface Listener{
        fun onClick(position: Int)
    }
}