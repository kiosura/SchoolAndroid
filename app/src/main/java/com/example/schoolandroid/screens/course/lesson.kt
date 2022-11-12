package com.example.schoolandroid.screens.course

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.TaskAdapter
import com.example.schoolandroid.data.Task
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.google.android.material.tabs.TabLayout


class lesson : BaseFragment(R.layout.fragment_lesson), Listener {

    private lateinit var recyclerView: RecyclerView
    private val task_adapter : TaskAdapter = TaskAdapter(this, R.layout.task_card_view)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycleTasks)
        with(recyclerView) {
            //setHasFixedSize(true)
            layoutManager = GridLayoutManager(view.context, 4)
            adapter = task_adapter
            //suppressLayout(true)
        }

        for (i in 1..40){
            task_adapter.addTask(Task(i, " hui", resources.getColor(R.color.purple_500)))
        }
    }

    override fun onClick(position: Int) {
        fragmentReplacer.replace(1, task.newInstance(tabSelected = position))
        val text = activity?.findViewById<TextView>(R.id.textView)!!
        text.text = "Домашнее задание"
    }

    companion object {
        @JvmStatic
        fun newInstance() = lesson()
    }
}