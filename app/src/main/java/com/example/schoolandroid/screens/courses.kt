package com.example.schoolandroid.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.CourseAdapter
import com.example.schoolandroid.data.Course


class courses : Fragment() {

    private lateinit var recycleView: RecyclerView
    val course_adapter = CourseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_courses, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.recycleCourses)
        with(recycleView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = course_adapter
        }
        course_adapter.addCourse(Course("course2"))
        course_adapter.addCourse(listOf(Course("course3"), Course("course4")))
    }

    companion object {
        @JvmStatic
        fun newInstance() = courses()
    }
}