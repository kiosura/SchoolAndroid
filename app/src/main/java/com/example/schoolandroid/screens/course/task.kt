package com.example.schoolandroid.screens.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.TaskAdapter
import com.example.schoolandroid.data.Task
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.google.android.material.internal.ViewUtils.dpToPx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher


@Suppress("DEPRECATION")
class task(private val tabSelected : Int) : BaseFragment(R.layout.task_view),
    Listener {

    private lateinit var recyclerView: RecyclerView
    private val task_adapter : TaskAdapter = TaskAdapter(this, R.layout.task_tab_view)
    private var lastIndex : Int = tabSelected

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
        lifecycleScope.launch {
            whenStarted {
                withContext(Dispatchers.IO) { getScroll(millisec=300) }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    suspend fun getScroll(millisec : Long, tab : Int = tabSelected){
        Thread.sleep(millisec)
        val nesty = activity?.findViewById<NestedScrollView>(R.id.nestedTabLayout)!!
        nesty.smoothScrollTo(0, (dpToPx(nesty.context, 50)*tab).toInt())
    }

    override fun onClick(position: Int) {
        if (position != lastIndex) {
            recyclerView.get(position).findViewById<Button>(R.id.taskBody)
                .setBackgroundColor(resources.getColor(R.color.teal_200))
            recyclerView.get(lastIndex).findViewById<Button>(R.id.taskBody)
                .setBackgroundColor(resources.getColor(R.color.purple_500))
            lastIndex = position
            lifecycleScope.launch {
                whenStarted {
                    withContext(Dispatchers.IO) { getScroll(millisec=50, tab=position) }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tabSelected : Int) = task(tabSelected)
    }
}