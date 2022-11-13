package com.example.schoolandroid.screens.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.TaskAdapter
import com.example.schoolandroid.data.Task
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.google.android.material.internal.ViewUtils.dpToPx


@Suppress("DEPRECATION")
class task(private val tabSelected : Int) : BaseFragment(R.layout.task_view),
    Listener {

    private lateinit var recyclerView: RecyclerView
    private val task_adapter : TaskAdapter = TaskAdapter(this, R.layout.task_tab_view)
    private var lastIndex : Int = tabSelected
    private lateinit var nesty : NestedScrollView

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.tabRecycler)
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = task_adapter
        }
        for (i in 1..40){
            if (i-1 == tabSelected) task_adapter.addTask(Task(i, " hui", resources.getColor(R.color.teal_200)))
            else task_adapter.addTask(Task(i, " hui", resources.getColor(R.color.purple_500)))
        }
        recyclerView.isVisible = false

        nesty = view.findViewById<NestedScrollView>(R.id.nestedTabLayout)

        view.findViewById<Button>(R.id.showAllTasks).setOnClickListener {
            recyclerView.isVisible = !recyclerView.isVisible
            if (recyclerView.isVisible) getScroll()
        }
        view.findViewById<Button>(R.id.nextTask).setOnClickListener {
            recyclerView.isVisible = false
            onClick(lastIndex+1)
        }
    }

    // .post (Runnable {} ) waiting for full initialization recyclerView (visible)
    @SuppressLint("RestrictedApi")
    fun getScroll(tab : Int = lastIndex)
        = nesty.post(Runnable { nesty.smoothScrollTo(0, (dpToPx(nesty.context, 50)*(if(tab > 2) tab-2 else 0)).toInt(), 200) })

    override fun onClick(position: Int) {
        if (position != lastIndex) {
            if (position == task_adapter.itemCount-1) view?.findViewById<Button>(R.id.nextTask)?.isVisible = false
            if (lastIndex == task_adapter.itemCount-1) view?.findViewById<Button>(R.id.nextTask)?.isVisible = true
            recyclerView.get(position).findViewById<Button>(R.id.taskBody)
                .setBackgroundColor(resources.getColor(R.color.teal_200))
            recyclerView.get(lastIndex).findViewById<Button>(R.id.taskBody)
                .setBackgroundColor(resources.getColor(R.color.purple_500))
            lastIndex = position
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tabSelected : Int) = task(tabSelected)
    }
}