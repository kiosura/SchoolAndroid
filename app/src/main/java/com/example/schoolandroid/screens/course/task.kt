package com.example.schoolandroid.screens.course

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.TaskAdapter
import com.example.schoolandroid.screens.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.GRAVITY_CENTER
import kotlinx.coroutines.selects.select


class task(private val adapter: TaskAdapter, private val tabSelected : Int) : BaseFragment(R.layout.task_view) {

    private lateinit var tabLayout : TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = view.findViewById(R.id.taskTopMenue)
        tabConstructor()
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.tabGravity = GRAVITY_CENTER
        tabLayout.getTabAt(tabSelected)!!.select()
    }

    private fun tabConstructor() {
        for (i in 0..adapter.itemCount-1){
            val tab = tabLayout.newTab()
            tab.text = adapter.getTask(i).id.toString()
            tabLayout.addTab(tab)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(adapter: TaskAdapter, tabSelected : Int) = task(adapter, tabSelected)
    }
}