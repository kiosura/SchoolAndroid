package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.LessonItem
import com.example.schoolandroid.data.Lessons
import com.example.schoolandroid.databinding.AboutCourseLessonCardViewBinding
import com.example.schoolandroid.interfaces.Listener

class AboutCourseLessonsAdapter(val listener: Listener) : RecyclerView.Adapter<AboutCourseLessonsAdapter.LessonHolder>() {
    private var lessons = ArrayList<LessonItem>()

    class LessonHolder(card : View) : RecyclerView.ViewHolder(card){
        private val binding = AboutCourseLessonCardViewBinding.bind(card)

        fun bind(lesson: LessonItem) = with(binding){
            lessonBody.text = lesson.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.about_course_lesson_card_view, parent,false)
            return LessonHolder(view)
    }

    override fun onBindViewHolder(holder: LessonHolder, position: Int) {
        holder.bind(lessons[position])
        holder.itemView.findViewById<Button>(R.id.lessonBody)
            .setOnClickListener(View.OnClickListener{
                listener.onClick(1)
            })
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun addLesson(lesson: Lessons){
        lessons = lesson
        notifyDataSetChanged()
    }
}