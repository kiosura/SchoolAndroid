package com.example.schoolandroid.screens.course

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.TaskAdapter
import com.example.schoolandroid.api.CourseViewModel
import com.example.schoolandroid.data.TaskItem
import com.example.schoolandroid.databinding.TaskViewBinding
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage
import com.google.android.material.internal.ViewUtils.dpToPx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class task(tabSelected : Int) : BaseFragment(R.layout.task_view),
    Listener {

    private lateinit var recyclerView: RecyclerView
    private val task_adapter : TaskAdapter = TaskAdapter(this, R.layout.task_tab_view)
    private var lastIndex : Int = tabSelected
    private var nextIndex : Int = 0
    private lateinit var nesty : NestedScrollView

    private lateinit var CourseVM : CourseViewModel

    private var taskCount : Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CourseVM = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
        taskCount = Storage.getCurrentCourse().value!!.lessons[CourseVM.lessonIndex].getTasks
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTask()

        nesty = view.findViewById<NestedScrollView>(R.id.nestedTabLayout)


        recyclerView = view.findViewById(R.id.tabRecycler)
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = task_adapter
        }

        Storage.getCurrentCourse().observe(viewLifecycleOwner){ course ->
            taskCount = course.lessons[CourseVM.lessonIndex].getTasks
            task_adapter.putTasks(null)
            for (i in 0 until taskCount){
                val color = if (lastIndex == i) resources.getColor(R.color.teal_200) else resources.getColor(R.color.purple_500)
                task_adapter.addTask(TaskItem(index = i+1, name="hui", color=color))
            }
        }


        if (taskCount < 2)  view.findViewById<Button>(R.id.nextTask).isVisible = false
        recyclerView.isVisible = false


        view.findViewById<Button>(R.id.showAllTasks).setOnClickListener {
            recyclerView.isVisible = !recyclerView.isVisible
            if (recyclerView.isVisible) getScroll()
        }
        view.findViewById<Button>(R.id.nextTask).setOnClickListener {
            recyclerView.isVisible = false
            if (lastIndex + nextIndex < taskCount-2) nextIndex += 1
            else if (lastIndex + nextIndex == taskCount-2){
                nextIndex += 1
                it.isVisible = false
            }
            getTask()
        }
    }

    // .post (Runnable {} ) waiting for full initialization recyclerView (visible)
    @SuppressLint("RestrictedApi")
    fun getScroll() {
        nesty.post(Runnable {
            onClick(lastIndex + nextIndex)
            nesty.smoothScrollTo(0,
                (dpToPx(nesty.context, 50)*(if(lastIndex + nextIndex > 2) lastIndex + nextIndex-2 else 0))
                    .toInt(), 200)
        })
    }

    override fun onClick(position: Int) {
        if (position != lastIndex) {
            if (position == task_adapter.itemCount-1) view?.findViewById<Button>(R.id.nextTask)?.isVisible = false
            if (lastIndex == task_adapter.itemCount-1) view?.findViewById<Button>(R.id.nextTask)?.isVisible = true
            recyclerView.get(lastIndex).findViewById<Button>(R.id.taskBody)
                .setBackgroundColor(resources.getColor(R.color.purple_500))
            recyclerView.get(position).findViewById<Button>(R.id.taskBody)
                .setBackgroundColor(resources.getColor(R.color.teal_200))
            lastIndex = position
            nextIndex = 0
            getTask()
        }
    }

    fun getTask() {
        lifecycleScope.launch(Dispatchers.IO) {
            val homework = Storage.getCurrentCourse().value!!.lessons[CourseVM.lessonIndex].homework
            if (homework == null) {
                Thread.sleep(500L)
                getTask()
            }
            val current_task = homework!!.tasks[lastIndex+nextIndex]
            val binding = TaskViewBinding.bind(requireView())
            with(binding) {
                taskName.text = current_task.name
                taskText.text = current_task.text
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tabSelected : Int) = task(tabSelected)
    }
}