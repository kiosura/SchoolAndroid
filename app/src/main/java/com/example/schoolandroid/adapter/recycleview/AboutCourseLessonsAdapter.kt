package com.example.schoolandroid.adapter.recycleview

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.data.LessonItem
import com.example.schoolandroid.data.Lessons
import com.example.schoolandroid.data.User
import com.example.schoolandroid.databinding.AboutCourseLessonCardViewBinding
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.storage.PersistentStorage
import com.example.schoolandroid.storage.Storage

class AboutCourseLessonsAdapter(val listener: Listener) : RecyclerView.Adapter<AboutCourseLessonsAdapter.LessonHolder>() {
    private var lessons = ArrayList<LessonItem>()
    private var progressToLessons : List<String>? = null

    class LessonHolder(card : View) : RecyclerView.ViewHolder(card) {
        private val binding = AboutCourseLessonCardViewBinding.bind(card)

        fun bind(lesson: LessonItem, percent : String = "") = with(binding) {
            lessonName.text = lesson.name
            lessonNumber.text = (lesson.index!!.plus(1)).toString()

            // If user is authenticated, showing progress bar
            if (PersistentStorage.getObject<User>().registered_datetime == null) {
                lessonProgressText.visibility = View.INVISIBLE
                lessonProgressBar.visibility = View.INVISIBLE
            }
            else {
                // Layout parameters - used in width changing while showing progress percentage
                val paramsProgressBarLeft = lessonProgressBarLeft.layoutParams
                val paramsProgressBarRight = lessonProgressBarRight.layoutParams

                if (percent != "") {
                    // Progress percentage in TextView
                    lessonProgressText.text = percentage(percent)

                    // Progress percentage in linear_layout bar
                    paramsProgressBarLeft.width = (lessonProgressBar.width * percent.toInt() / 100)
                    paramsProgressBarRight.width = lessonProgressBar.width - paramsProgressBarLeft.width
                }
                else paramsProgressBarRight.width = lessonProgressBar.width

                // Setting new background for the progress bar with all corners rounded
                lessonProgressBarRight.setBackgroundResource(R.drawable.rounded_corner_grey)

                // Setting new width values for both sides of progress bar
                lessonProgressBarLeft.setLayoutParams(paramsProgressBarLeft)
                lessonProgressBarRight.setLayoutParams(paramsProgressBarRight)
            }
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

    fun addLesson(lessonsList: Lessons){
        lessons = lessonsList
        notifyDataSetChanged()
    }
}