package com.example.schoolandroid.screens.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.CourseAdapter
import com.example.schoolandroid.adapter.TaskAdapter
import com.example.schoolandroid.data.Course
import com.example.schoolandroid.data.Task


class course : Fragment() {

    private lateinit var recycleView: RecyclerView
    val task_adapter = TaskAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_course, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.recycleTasks)
        with(recycleView) {
            //setHasFixedSize(true)
            layoutManager = GridLayoutManager(view.context, 4)
            adapter = task_adapter
        }

        for (i in 1..12){
            task_adapter.addTask(Task(i, " hui"))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = course()
    }
}