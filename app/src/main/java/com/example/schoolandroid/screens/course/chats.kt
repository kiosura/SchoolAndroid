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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.ChatsAdapter
import com.example.schoolandroid.data.Chat
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

        courseObserver()
    }

    fun courseObserver() {
        if (view != null) lifecycleScope.launch(Dispatchers.Main) {
            Storage.getCurrentCourse().observe(viewLifecycleOwner) { course ->
                chats_adapter.addChat(course.chats)
            }
        }
    }

    override fun onClick(position : Int) {
        val link = chats_adapter.getLink(position)
        if (link != null) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent)
        }
    }

    override fun onClickMore(position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() = chats()
    }
}