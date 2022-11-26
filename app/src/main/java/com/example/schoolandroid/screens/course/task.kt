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
    private val course = Storage.getCurrentCourse().value!!
    private var taskCount : Int = 0
    private lateinit var binding : TaskViewBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CourseVM = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
        taskCount = course.lessons[CourseVM.lessonIndex].getTasks
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TaskViewBinding.bind(view)
        getTask()

        nesty = binding.nestedTabLayout


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
        // Storage.currentCourse for course.id, CourseViewModel.lesson_index, taskIndex
        if (Storage.getUser().value!!.registered_datetime != null) {
            Storage.getUser().observe(viewLifecycleOwner) { user ->
                with(binding){
                    with(editTextAnswer) {
                        hint = user.progresses!!.findProgress(course.id)!!.getTaskProgress(CourseVM.lessonIndex, taskIndex()).toString()
                        setTextIsSelectable(false)
        } } } }


        if (taskCount < 2)  binding.nextTask.isVisible = false
        recyclerView.isVisible = false


        binding.sendAnswer.setOnClickListener { answerPost() }
        binding.showAllTasks.setOnClickListener {
            recyclerView.isVisible = !recyclerView.isVisible
            if (recyclerView.isVisible) getScroll()
        }
        binding.nextTask.setOnClickListener {
            recyclerView.isVisible = false
            if (taskIndex() < taskCount-2) nextIndex += 1
            else if (taskIndex() == taskCount-2){
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
            onClick(taskIndex())
            nesty.smoothScrollTo(0,
                (dpToPx(nesty.context, 50)*(if(taskIndex() > 2) taskIndex()-2 else 0))
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
            val current_task = homework!!.tasks[taskIndex()]
            with(binding) {
                taskName.text = current_task.name
                taskText.text = current_task.text
            }
        }
    }

    fun answerPost() {
        val answer = binding.sendText.text.toString()
        CourseVM.postAnswer(text = answer, taskIndex = taskIndex())
    }

    fun taskIndex() : Int = lastIndex + nextIndex

    companion object {
        @JvmStatic
        fun newInstance(tabSelected : Int) = task(tabSelected)
    }
}