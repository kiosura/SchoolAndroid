package com.example.schoolandroid.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.schoolandroid.R
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.data.Teacher
import com.example.schoolandroid.databinding.FragmentProfileBinding
import com.example.schoolandroid.databinding.TeacherDescriptionLayoutBinding
import com.example.schoolandroid.storage.Storage

class TeacherDescriptionDialog : DialogFragment() {

    private lateinit var teacher : Teacher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.teacher_description_layout, container, false)

//        view.findViewById<Button>(R.id.closeteacher).setOnClickListener {
//            dialog?.cancel()
//        }
        val binding = TeacherDescriptionLayoutBinding.bind(view)
        fun bind() = with(binding) {
            fun makeTeacherName(teacher : Teacher) : String {
                return teacher.name + " " + teacher.surname
            }

            courseTutor.text = makeTeacherName(teacher)
            briefdescriptions.text = teacher.description
        }

        getBundle()
        bind()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<TextView>(R.id.teacherdescription).text =  teacher.description

    }

    private fun getBundle() {
        if (requireArguments().getBoolean("isCurrent", false)) {
            teacher = Storage.getCurrentCourse().value?.teachers?.get(0)!!
        }
        else {
            val position = requireArguments().getInt("position")
            val isMy = requireArguments().getBoolean("isMy", false)
            teacher = Storage.getCourses(isMy).value?.get(position)?.teachers?.get(0)!!
        }
    }

}