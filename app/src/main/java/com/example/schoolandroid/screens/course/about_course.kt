package com.example.schoolandroid.screens.course

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.AboutCourseLessonsAdapter
import com.example.schoolandroid.api.CourseViewModel
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.data.ProgressItem
import com.example.schoolandroid.data.Progresses
import com.example.schoolandroid.databinding.FragmentAboutCourseBinding
import com.example.schoolandroid.dialogs.TeacherDescriptionDialog
//import com.example.schoolandroid.api.FirstApi.Companion.apiBase
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
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
            currentCourse.observe(viewLifecycleOwner) { courseItem ->
                fun makeTeacherName(courseItem : CourseItem) : String {
                    return courseItem.teachers[0].name + " " + courseItem.teachers[0].surname
                }
                courseName.text = courseItem.name
                courseDescription.text = courseItem.description
                courseTutor.text = makeTeacherName(courseItem)
                courseTutorDescription.text = courseItem.teachers[0].description
                with(recycleLessons) {
                    layoutManager = LinearLayoutManager(view.context)
                    adapter = lessonsAdapter
                }

            }
        }
        bind()

        courseObserver()
        userProgressObserver()

        binding.courseTutorMore.setOnClickListener {
            Storage.getCurrentCourse().value?.let { it1 -> this.onClickMore(it1.id) }
        }
    }

    fun courseObserver() {
        if (view != null) lifecycleScope.launch(Dispatchers.Main) {
            Storage.getCurrentCourse().observe(viewLifecycleOwner) {list ->
                lessonsAdapter.addLesson(list.lessons)
            }
        }
    }

    fun userProgressObserver() {
        if (view != null) lifecycleScope.launch(Dispatchers.Main) {
            Storage.getUser().observe(viewLifecycleOwner) { user ->
                val progress = user.progresses?.findProgress(Storage.getCurrentCourse().value!!.id)
                if (progress != null) {
                    lessonsAdapter.addProgressToLessons(progress.getLessons())
                }
            }
        }
    }

    override fun onClick(position: Int) {
        fragmentReplacer.replaceDefault(1)
        CourseVM.lessonIndex = position
        val tabLayout : TabLayout? = activity?.findViewById<TabLayout>(R.id.coursePageBottomMenue)
        val tab = tabLayout?.getTabAt(1)
        tab?.select()
    }

    override fun onClickMore(course_id : Int) {
        arguments?.putInt(course_id.toString(), course_id!!)
        var dialog = TeacherDescriptionDialog()
        dialog.arguments
        dialog.show(activity?.supportFragmentManager!!, "About Teacher")
    }

    companion object {
        @JvmStatic
        fun newInstance() = about_course()
    }
}
