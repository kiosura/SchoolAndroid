package com.example.schoolandroid.adapter.recycleview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.activity.CourseActivity
import com.example.schoolandroid.activity.MainActivity.Companion.extrahui
import com.example.schoolandroid.data.Course
import com.example.schoolandroid.data.CourseItem
import com.example.schoolandroid.databinding.CourseCardViewBinding



class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseHolder>() {

    var courses = emptyList<CourseItem>()

    class CourseHolder(card : View) : RecyclerView.ViewHolder(card){
        val binding = CourseCardViewBinding.bind(card)
        fun bind(course: CourseItem) = with(binding) {
            binding.coursename.text = course.name
            binding.coursedescription.text = course.product_preview
            binding.coursetutor.text = course.teachers[0].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card_view, parent, false)
        return CourseHolder(view)
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {

        holder.bind(courses[position])

        val context : Context = holder.itemView.context

        holder.itemView.findViewById<LinearLayout>(R.id.courseBody)
            .setOnClickListener {
            val intent: Intent = Intent(context, CourseActivity::class.java)
            intent.putExtra(extrahui, position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }


    fun addCourse(list: Course) {
        courses = list
        notifyDataSetChanged()
    }
}