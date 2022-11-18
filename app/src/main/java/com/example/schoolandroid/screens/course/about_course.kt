package com.example.schoolandroid.screens.course

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.AboutCourseLessonsAdapter
//import com.example.schoolandroid.api.FirstApi.Companion.apiBase
import com.example.schoolandroid.data.LessonItem
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage
import com.google.android.material.tabs.TabLayout


class about_course : BaseFragment(R.layout.fragment_about_course), Listener {
    private lateinit var recycleView: RecyclerView
    val lessonsAdapter : AboutCourseLessonsAdapter = AboutCourseLessonsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.recycleLessons)
        with(recycleView) {
            layoutManager = LinearLayoutManager(view.context)
            adapter = lessonsAdapter
        }

        Storage.getLessons().observe(viewLifecycleOwner) { list ->
            lessonsAdapter.addLesson(list)
        }
    }

    override fun onClick(int: Int) {
        fragmentReplacer.replaceDef(1)
        val tabLayout : TabLayout? = activity?.findViewById<TabLayout>(R.id.coursePageBottomMenue)
        val tab = tabLayout?.getTabAt(1)
        tab?.select()
    }

    companion object {
        @JvmStatic
        fun newInstance() = about_course()
    }
}