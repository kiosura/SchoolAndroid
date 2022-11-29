package com.example.schoolandroid.adapter.recycleview

import com.example.schoolandroid.interfaces.Listener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.databinding.CourseCardViewReducedBinding

class ProfileCoursesAdapter(val listener: Listener) : RecyclerView.Adapter<ProfileCoursesAdapter.CourseProfileHolder>() {

    var courses : ArrayList<CourseItem> = ArrayList()

    class CourseProfileHolder(card : View) : RecyclerView.ViewHolder(card) {
        val binding = CourseCardViewReducedBinding.bind(card)
        fun bind(course : CourseItem) = with(binding) {
            courseNameProfile.text = course.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseProfileHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card_view_reduced, parent, false)
        return CourseProfileHolder(view)
    }

    override fun onBindViewHolder(holder: CourseProfileHolder, position: Int) {
        holder.bind(courses[position])

        holder.itemView.findViewById<LinearLayout>(R.id.courseBodyProfile)
            .setOnClickListener{
                listener.onClick(position)
            }
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun addCourse(coursesList: Courses) {
        courses = coursesList
        notifyDataSetChanged()
    }
}