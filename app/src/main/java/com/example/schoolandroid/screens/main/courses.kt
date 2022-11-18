package com.example.schoolandroid.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.activity.CourseActivity
import com.example.schoolandroid.activity.MainActivity
import com.example.schoolandroid.adapter.recycleview.CourseAdapter
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.dialogs.FilterCoursesDialog
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.storage.Storage


class courses : Fragment(), Listener {

    private lateinit var recycleView: RecyclerView
    val course_adapter = CourseAdapter(this)

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

        // subscription for MutableLiveData<Courses> changes - coming from Storage
        Storage.getCourses().observe(viewLifecycleOwner) { list ->
            course_adapter.addCourses(list)
        }

        // custom view model for main activity
        val storageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        // subscription for MutableLiveData<Response<Course.lessons>> changes - coming from API
        val lessonsRaw = storageViewModel.getLessons()
        lessonsRaw.observe(this.viewLifecycleOwner) {
            Storage.mergeCourses(lessonsRaw)
        }

        courseBaseName = activity?.findViewById<TextView>(R.id.textView)!!
        switchContainer = view.findViewById<LinearLayout>(R.id.switchWithCourses)!!
        switch = switchContainer.findViewById<SwitchCompat>(R.id.switch1)!!

        switchContainer.setOnClickListener {
            switch.isChecked = !switch.isChecked
            if (switch.isChecked) courseBaseName.text = courseNames[1]
            else courseBaseName.text = courseNames[0]
        }
    }

    override fun onClick(position: Int) {
        Storage.setCurrent(position)
        val intent: Intent = Intent(context, CourseActivity::class.java)
        context?.startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = courses()
    }
}