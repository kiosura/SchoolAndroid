package com.example.schoolandroid.screens.course

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.api.FirstApi.Companion.apiBase
import com.example.schoolandroid.screens.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class about_course : BaseFragment(R.layout.fragment_about_course) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.recycleLessons)
        with(recycleView) {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = lessonsAdapter
            //suppressLayout(true)
        }

        lessonsAdapter.addLesson(listOf(Lesson("lesson1"), Lesson("lesson2")))

        for (i in 3..40){
            lessonsAdapter.addLesson(Lesson("lesson" + i.toString()))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = about_course()
    }

    override fun onClick(int: Int) {
        val tabLayout : TabLayout? = activity?.findViewById<TabLayout>(R.id.coursePageBottomMenue)
        val tab = tabLayout?.getTabAt(1)
        tab?.select()
    }
}