package com.example.schoolandroid.screens.course

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.AboutCourseLessonsAdapter
import com.example.schoolandroid.api.CourseViewModel
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.databinding.FragmentAboutCourseBinding
//import com.example.schoolandroid.api.FirstApi.Companion.apiBase
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch


class about_course : BaseFragment(R.layout.fragment_about_course), Listener {
    private val lessonsAdapter : AboutCourseLessonsAdapter = AboutCourseLessonsAdapter(this)

    private lateinit var CourseVM : CourseViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        CourseVM = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAboutCourseBinding.bind(view)
        fun bind() = with(binding) {
            val currentCourse = Storage.getCurrentCourse()
            currentCourse.observe(viewLifecycleOwner) { list ->
                fun makeTeacherName(list : CourseItem) : String {
                    return list.teachers[0].name + " " + list.teachers[0].surname
                }
                courseName.text = list.name
                courseDescription.text = list.product_preview
                courseTutor.text = makeTeacherName(list)
                with(recycleLessons) {
                    layoutManager = LinearLayoutManager(view.context)
                    adapter = lessonsAdapter
                }

            }
        }
        bind()

        val lessonRaw = Storage.getCurrentCourse()
        lessonRaw.observe(this.viewLifecycleOwner) {list ->
            lessonsAdapter.addLesson(list.lessons)
        }
    }

    override fun onClick(position: Int) {
        fragmentReplacer.replaceDefault(1)
        CourseVM.lessonIndex = position-1
        CourseVM.getLesson()
        val tabLayout : TabLayout? = activity?.findViewById<TabLayout>(R.id.coursePageBottomMenue)
        val tab = tabLayout?.getTabAt(1)
        tab?.select()
    }

    companion object {
        @JvmStatic
        fun newInstance() = about_course()
    }
}
