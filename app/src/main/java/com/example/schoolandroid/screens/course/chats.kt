package com.example.schoolandroid.screens.course

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.ChatsAdapter
import com.example.schoolandroid.data.Chat
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment


class chats : BaseFragment(R.layout.fragment_chats), Listener {

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
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+YU0qCPD0B8dhOWYy"));
        startActivity(browserIntent)
    }
}