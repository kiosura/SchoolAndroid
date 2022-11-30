package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.databinding.CourseCardViewBinding
import com.example.schoolandroid.interfaces.Listener


class CourseAdapter(val listener : Listener, val card_layout : Int) : RecyclerView.Adapter<CourseAdapter.CourseHolder>() {

    var courses = Courses()

    class CourseHolder(card : View) : RecyclerView.ViewHolder(card) {
        val binding = CourseCardViewBinding.bind(card)
        fun bind(course: CourseItem) = with(binding) {
            coursename.text = course.name
            coursedescription.text = course.product_preview
            coursetutor.text = makeTeacherName(course)
            courseTutorDescription.text = course.teachers[0].description
        }
        fun makeTeacherName(course : CourseItem) : String {
            return course.teachers[0].name + " " + course.teachers[0].surname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context).inflate(card_layout, parent, false)
        return CourseHolder(view)
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {

        holder.bind(courses[position])

        holder.itemView.findViewById<LinearLayout>(R.id.courseBody)
            .setOnClickListener {
            listener.onClick(position)
        }

        holder.itemView.findViewById<TextView>(R.id.courseTutorMore)
            .setOnClickListener {
                listener.onClickMore(position)
            }
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun addCourses(list: Courses?) {
        if (list != null) courses = list
        else courses.clear()
        notifyDataSetChanged()
    }
}