package com.example.schoolandroid.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.PushAdapter
import com.example.schoolandroid.data.Push


class PushDialog : DialogFragment() {

    private lateinit var recycleView: RecyclerView
    val push_adapter = PushAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.push_layout, container, false)

        view.findViewById<Button>(R.id.closepush).setOnClickListener {
            dialog?.cancel()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.recyclePushes)
        with(recycleView){
            layoutManager = LinearLayoutManager(view.context)
            adapter = push_adapter
        }

        push_adapter.addPush(Push("push1"))
        push_adapter.addPush(listOf(Push("push2"), Push("push3")))

        for (i in 4..10){
            push_adapter.addPush(Push("push" + i.toString()))
        }


    }
}
