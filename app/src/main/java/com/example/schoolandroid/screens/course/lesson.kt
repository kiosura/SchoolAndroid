package com.example.schoolandroid.screens.course

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
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

    private lateinit var recycleView: RecyclerView
    var task_adapter : TaskAdapter = TaskAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleView = view.findViewById(R.id.recycleTasks)
        with(recycleView) {
            //setHasFixedSize(true)
            layoutManager = GridLayoutManager(view.context, 4)
            adapter = task_adapter
            //suppressLayout(true)
        }

        for (i in 1..40){
            task_adapter.addTask(Task(i, " hui"))
        }
    }

    override fun onStart() {
        super.onStart()
        val backtolesson = activity?.findViewById<Button>(R.id.backtoMain)
        backtolesson?.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onClick(position: Int) {
        fragmentReplacer.replace(1, task.newInstance())
    }

    companion object {
        @JvmStatic
        fun newInstance() = lesson()
    }
}