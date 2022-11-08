package com.example.schoolandroid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.activity.CourseActivity
import com.example.schoolandroid.activity.MainActivity.Companion.extrahui
import com.example.schoolandroid.data.Course
import com.example.schoolandroid.databinding.CourseCardViewBinding


class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseHolder>() {

    private val courses = ArrayList<Course>()

    class CourseHolder(card : View) : RecyclerView.ViewHolder(card){
        val binding = CourseCardViewBinding.bind(card)
        fun bind(course: Course) = with(binding){
            textView2.text = course.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card_view, parent, false)
        return CourseHolder(view)
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {
        holder.bind(courses[position])
        val context : Context = holder.itemView.context
        holder.itemView.findViewById<Button>(R.id.textView2)
            .setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(context, CourseActivity::class.java)
            intent.putExtra(extrahui, position)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun addCourse(course : Course){
        courses.add(course)
        notifyDataSetChanged()
    }
    fun addCourse(course : List<Course>){
        courses.addAll(course)
        notifyDataSetChanged()
    }
}