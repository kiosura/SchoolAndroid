package com.example.schoolandroid.screens.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.ChatsAdapter
import com.example.schoolandroid.data.Chat
import com.example.schoolandroid.screens.BaseFragment


class chats : BaseFragment(R.layout.fragment_chats), ChatsAdapter.Listener {

    private lateinit var recycleView: RecyclerView
    val chats_adapter = ChatsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleView = view.findViewById(R.id.recycleChats)
        with(recycleView) {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = chats_adapter
        }
        chats_adapter.addChat(Chat("chat1"))
        chats_adapter.addChat(listOf(Chat("chat2"), Chat("chat3")))

        for (i in 4..17){
            chats_adapter.addChat(Chat("chat" + i.toString()))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = chats()
    }

    override fun onClick(position : Int) {
        Toast.makeText(context,"Clicked on chat № ${position}" ,Toast.LENGTH_LONG).show()
        println("34iuo5hrlkjewhtlkh34o2y7583476583746583746857345")
    }
}