package com.example.schoolandroid.screens.course

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.TaskAdapter
import com.example.schoolandroid.data.Task
import com.example.schoolandroid.screens.BaseFragment

class task : BaseFragment(R.layout.task_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backtolesson = activity?.findViewById<Button>(R.id.backtoMain)
    }

    companion object {
        @JvmStatic
        fun newInstance() = task()
    }
}