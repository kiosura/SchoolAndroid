package com.example.schoolandroid.adapter.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.LessonItem
import com.example.schoolandroid.data.Lessons
import com.example.schoolandroid.databinding.AboutCourseLessonCardViewBinding
import com.example.schoolandroid.interfaces.Listener

class AboutCourseLessonsAdapter(val listener: Listener) : RecyclerView.Adapter<AboutCourseLessonsAdapter.LessonHolder>() {
    private var lessons = ArrayList<LessonItem>()
    private var progressToLessons : List<String>? = null

    class LessonHolder(card : View) : RecyclerView.ViewHolder(card) {
        private val binding = AboutCourseLessonCardViewBinding.bind(card)

        fun bind(lesson: LessonItem, percent : String = "") = with(binding) {
            lessonName.text = lesson.name
            lessonNumber.text = (lesson.index!!.plus(1)).toString()
            lessonPercentProgress.text = percentage(percent)
        }
        private fun percentage(percent: String) : String {
            var newPercent = ""
            if (percent != "") {
               newPercent = "$percent%"
            }
            return newPercent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.about_course_lesson_card_view, parent,false)
            return LessonHolder(view)
    }

    override fun onBindViewHolder(holder: LessonHolder, position: Int) {
        if (progressToLessons != null) holder.bind(lessons[position], progressToLessons!![position])
        else holder.bind(lessons[position])

        holder.itemView.findViewById<LinearLayout>(R.id.lessonBody)
            .setOnClickListener(View.OnClickListener{
                listener.onClick(position)
            })
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun addProgressToLessons(list : List<String>?) {
        progressToLessons = list
        notifyDataSetChanged()
    }

    fun addLesson(lesson: Lessons){
        lessons = lesson
        notifyDataSetChanged()
    }
}