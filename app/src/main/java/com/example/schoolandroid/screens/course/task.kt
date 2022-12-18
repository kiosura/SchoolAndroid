package com.example.schoolandroid.screens.course

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
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
import com.example.schoolandroid.api.StorageViewModel
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

    private lateinit var StorageVM : StorageViewModel
    private lateinit var CourseVM : CourseViewModel
    private val course = Storage.getCurrentCourse().value!!
    private var taskCount : Int = 0
    private lateinit var binding : TaskViewBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CourseVM = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
        StorageVM = ViewModelProvider(requireActivity()).get(StorageViewModel::class.java)
        taskCount = course.lessons[CourseVM.lessonIndex].getTasks!!
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

        if (taskCount < 2 || taskCount == lastIndex+1)  binding.nextTask.isVisible = false
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
    private fun getScroll() {
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
            recyclerView.get(lastIndex).findViewById<LinearLayout>(R.id.taskBody)
                .setBackgroundResource(R.drawable.rounded_corner_coral)
            recyclerView.get(position).findViewById<LinearLayout>(R.id.taskBody)
                .setBackgroundResource(R.drawable.rounded_corner_grey)
            lastIndex = position
            nextIndex = 0
            getTask()
        }
    }

    // receive info with current task
    private fun getTask() {
        lifecycleScope.launch(Dispatchers.Main) {
            val homework = course.lessons[CourseVM.lessonIndex].homework
            if (homework == null) {
                Thread.sleep(500L)
                getTask()
                return@launch
            }
            val current_task = homework.tasks[taskIndex()]
            with(binding) {
                taskName.text = current_task.name
                taskText.text = current_task.text
            }
        }
        checkProgressForNow()
    }

    private fun observeCourse(){
        lifecycleScope.launch(Dispatchers.Main) {
            Storage.getCurrentCourse().observe(viewLifecycleOwner){
                task_adapter.putTasks(null)
                for (i in 0 until taskCount){
                    task_adapter.addTask(TaskItem(index=i+1, drawable = if (taskIndex() == i)
                        R.drawable.rounded_corner_coral else R.drawable.rounded_corner_grey))
                }
            }
        }
    }

    // Storage.currentCourse for course.id, CourseViewModel.lesson_index, taskIndex - for checking statusCode of current Task
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkProgressForNow() {
        if (view != null) lifecycleScope.launch(Dispatchers.Main) {
            Storage.getUser().removeObservers(viewLifecycleOwner)
            observeCourse()
            if (Storage.getUser().value?.registered_datetime != null) {
                Storage.getUser().observe(viewLifecycleOwner) { user ->
                    val progressTask = user.progresses?.findProgress(course.id)?.getTaskProgress(CourseVM.lessonIndex, taskIndex())
                    if (progressTask != null) {
                        with(binding) {
                            when (progressTask.second ) {
                                0 -> with(editTextAnswer) {  }
                                1 -> {
                                    with(editAnswerContainer) {
                                        background = resources.getDrawable(R.drawable.border_task_truth)
                                    }
                                    with(editTextAnswer) {
                                        isEnabled = false
                                        text = Editable.Factory.getInstance().newEditable(progressTask.first.toString())
                                    }
                                    with(sendAnswer) {
                                        isVisible = false
                                    }
                                }
                                2 -> {
                                    with(editAnswerContainer) {
                                        background = resources.getDrawable(R.drawable.border_task_false)
                                    }
                                }
                            }
            } } } } }
    }

    private fun answerPost() {
        if (Storage.getUser().value?.id != null) {
            val answer = binding.editTextAnswer.editableText.toString()
            CourseVM.postAnswer(text = answer, taskIndex = taskIndex(), storageVM = StorageViewModel())
        }
        else {
            Toast.makeText(this.context, "Зарегистрируйтесь, чтобы отправлять ответы на задания", Toast.LENGTH_SHORT).show()
        }
    }

    private fun taskIndex() : Int = lastIndex + nextIndex

    companion object {
        @JvmStatic
        fun newInstance(tabSelected : Int) = task(tabSelected)
    }
}