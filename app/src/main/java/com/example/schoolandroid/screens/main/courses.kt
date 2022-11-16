package com.example.schoolandroid.screens.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.recycleview.CourseAdapter
import com.example.schoolandroid.api.CourseViewModel
import com.example.schoolandroid.data.Course
import com.example.schoolandroid.dialogs.FilterCoursesDialog


class courses : Fragment() {

    private lateinit var recycleView: RecyclerView
    val course_adapter = CourseAdapter()

    private val courseNames : List<String> = listOf(
        "Все курсы",
        "Мои курсы"
    )

    private lateinit var courseBaseName : TextView
    private lateinit var switchContainer : LinearLayout
    private lateinit var switch : SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_courses, container, false)

        val buttonFilter = view.findViewById<LinearLayout>(R.id.filterWithCourses)
        buttonFilter.setOnClickListener {
            val FilterCoursesDialogFragment = FilterCoursesDialog()
            FilterCoursesDialogFragment.show(childFragmentManager, "Filter")
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleView = view.findViewById(R.id.recycleCourses)
        with(recycleView) {
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = course_adapter
        }

        var viewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        viewModel.getCourses().observe(viewLifecycleOwner) { list ->
            list.body()?.let { course_adapter.addCourse(it) }
        }


//        for (i in 1..20){
//            course_adapter.addCourse(Course("course" + i.toString()))
//        }
//
        courseBaseName = activity?.findViewById<TextView>(R.id.textView)!!
        switchContainer = view.findViewById<LinearLayout>(R.id.switchWithCourses)!!
        switch = switchContainer.findViewById<SwitchCompat>(R.id.switch1)!!

        switchContainer.setOnClickListener {
            switch.isChecked = !switch.isChecked
            if (switch.isChecked) courseBaseName.text = courseNames[1]
            else courseBaseName.text = courseNames[0]
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = courses()
    }
}