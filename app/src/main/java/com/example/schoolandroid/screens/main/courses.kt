package com.example.schoolandroid.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.activity.CourseActivity
import com.example.schoolandroid.activity.MainActivity
import com.example.schoolandroid.adapter.recycleview.CourseAdapter
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.databinding.FragmentCoursesBinding
import com.example.schoolandroid.dialogs.FilterCoursesDialog
import com.example.schoolandroid.dialogs.TeacherDescriptionDialog
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class courses : BaseFragment(R.layout.fragment_courses), Listener {

    private lateinit var binding : FragmentCoursesBinding

    private lateinit var recycleView: RecyclerView
    // раздать разные cardview
    private val course_adapter = CourseAdapter(this, R.layout.course_card_view)
    private val my_course_adapter = CourseAdapter(this, R.layout.course_card_view)
    private lateinit var storageViewModel: StorageViewModel

    private val courseNames : List<String> = listOf(
        "Все курсы",
        "Мои курсы"
    )

    private lateinit var courseBaseName : TextView
    private lateinit var switchContainer : LinearLayout
    private lateinit var switch : SwitchCompat

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoursesBinding.bind(view)

        val buttonFilter = view.findViewById<LinearLayout>(R.id.filterWithCourses)
        buttonFilter.setOnClickListener {
            val FilterCoursesDialogFragment = FilterCoursesDialog()
            FilterCoursesDialogFragment.show(childFragmentManager, "Filter")
        }

        // дописать заход на my_courses_adapter после подготовки UserData
        recycleView = view.findViewById(R.id.recycleCourses)
        with(recycleView) {
            layoutManager = LinearLayoutManager(view.context)
            adapter = course_adapter
        }

        // subscription for MutableLiveData<Courses> changes - coming from Storage
        courseObserver()
        myCourseObserver()

        courseBaseName = activity?.findViewById<TextView>(R.id.textView)!!
        switchContainer = view.findViewById<LinearLayout>(R.id.switchWithCourses)
        switch = switchContainer.findViewById<SwitchCompat>(R.id.switch1)

        switchContainer.setOnClickListener {
            switch.isChecked = !switch.isChecked
            if (switch.isChecked) onChangeToSelf()
            else onChangeToAll()
        }
    }

    private fun courseObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            Storage.getCourses().observe(requireActivity()) { list ->
                course_adapter.addCourses(list)
            }
        }
    }

    private fun myCourseObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            Storage.getCourses(isMy = true).observe(requireActivity()) { list ->
                my_course_adapter.addCourses(list)
            }
        }
    }

    private fun onChangeToSelf() {
        courseBaseName.text = courseNames[1]
        recycleView.adapter = my_course_adapter
    }

    private fun onChangeToAll() {
        courseBaseName.text = courseNames[0]
        recycleView.adapter = course_adapter
    }

    override fun onClick(position: Int) {
        Storage.setCurrent(position, isMy = recycleView.adapter == my_course_adapter)
        val intent: Intent = Intent(context, CourseActivity::class.java)
        context?.startActivity(intent)
    }

    override fun onClickMore(position: Int) {
        val course_id = Storage.getCourses().value?.get(position)?.id
        arguments?.putInt(course_id.toString(), course_id!!)
        var dialog = TeacherDescriptionDialog()
        dialog.arguments
        dialog.show(activity?.supportFragmentManager!!, "About Teacher")

    }

    companion object {
        @JvmStatic
        fun newInstance() = courses()
    }
}
