package com.example.schoolandroid.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.schoolandroid.R
import com.example.schoolandroid.databinding.FragmentProfileBinding
import com.example.schoolandroid.storage.Storage

class TeacherDescriptionDialog : DialogFragment() {

    var course_id: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.teacher_description_layout, container, false)

        view.findViewById<Button>(R.id.closeteacher).setOnClickListener {
            dialog?.cancel()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var course_id =  this.arguments

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teacher_description = Storage.getCourses().value?.get(course_id)?.teachers?.get(0)?.description
        println(teacher_description)
        view.findViewById<TextView>(R.id.teacherdescription).text =  teacher_description

    }



}