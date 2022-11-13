package com.example.schoolandroid.screens.course

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.FilesAdapter
import com.example.schoolandroid.adapter.recycleview.TaskAdapter
import com.example.schoolandroid.data.File
import com.example.schoolandroid.data.Task
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment


class lesson : BaseFragment(R.layout.fragment_lesson), Listener {

    private lateinit var recyclerViewFiles: RecyclerView
    private lateinit var recyclerViewTasks: RecyclerView
    private val task_adapter : TaskAdapter = TaskAdapter(this, R.layout.task_card_view)
    private val file_adapter : FilesAdapter = FilesAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lessonPlan = view.findViewById<LinearLayout>(R.id.lessonPlan)
        lessonPlan.isVisible = false

        recyclerViewFiles = view.findViewById(R.id.filesRecycler)
        with(recyclerViewFiles) {
            layoutManager = LinearLayoutManager(view.context)
            adapter = file_adapter
        }

        for (i in 1..3){
            file_adapter.addFile(File("file$i"))
        }
        recyclerViewFiles.isVisible = false

        view.findViewById<Button>(R.id.filesButton)
            .setOnClickListener {
                recyclerViewFiles.isVisible = !recyclerViewFiles.isVisible
            }

        view.findViewById<Button>(R.id.lessonPlanButton)
            .setOnClickListener {
                lessonPlan.isVisible = !lessonPlan.isVisible
            }

        recyclerViewTasks = view.findViewById(R.id.recycleTasks)
        with(recyclerViewTasks) {
            layoutManager = GridLayoutManager(view.context, 4)
            adapter = task_adapter
        }

        for (i in 1..40){
            task_adapter.addTask(Task(i, " hui", resources.getColor(R.color.purple_500)))
        }

        view.findViewById<Button>(R.id.progress).setOnClickListener{
            onClick(0)
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