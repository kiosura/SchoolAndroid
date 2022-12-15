package com.example.schoolandroid.adapter.recycleview

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.ui.res.colorResource
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

            val paramsProgressContainer = progressContainer.layoutParams
            val paramsLockSymbol = lockSymbol.layoutParams
            val paramsProgressBarLeft = lessonProgressBarLeft.layoutParams
            val paramsProgressBarRight = lessonProgressBarRight.layoutParams
            val paramsLessonInfoContainer = lessonInfoContainer.layoutParams

            fun disableLesson() {
                lessonProgressText.visibility = View.INVISIBLE
                lessonProgressBar.visibility = View.INVISIBLE

                paramsProgressContainer.height = 0
                paramsLessonInfoContainer.width = 500

                lessonBody.isEnabled = false
                lessonBody.setBackgroundResource(R.drawable.rounded_corner_light_grey)
            }

            if (Storage.getUser().value?.registered_datetime != null) {
                if (lesson.getAccess()) {
                    when (percent) {
                        "", "0" -> {
                            paramsProgressBarRight.width = lessonProgressBar.width
                            lessonProgressBarRight.setBackgroundResource(R.drawable.rounded_corner_grey)
                        }
                        else -> {
                            if (percent == "100") lessonProgressBarLeft.setBackgroundResource(R.drawable.rounded_corner_green)

                            // Progress percentage in TextView
                            lessonProgressText.text = percentage(percent)

                            // Progress percentage in linear_layout bar
                            paramsProgressBarLeft.width = (lessonProgressBar.width * percent.toInt() / 100)
                            paramsProgressBarRight.width = lessonProgressBar.width - paramsProgressBarLeft.width
                        }
                    }
                }
                else disableLesson()

            }
            else {
                if (!lesson.getAccess()) disableLesson()
                lessonProgressText.visibility = View.INVISIBLE
                lessonProgressBar.visibility = View.INVISIBLE
                paramsProgressContainer.height = 0
            }

            progressContainer.setLayoutParams(paramsProgressContainer)
            lockSymbol.setLayoutParams(paramsLockSymbol)
            lessonProgressBarLeft.setLayoutParams(paramsProgressBarLeft)
            lessonProgressBarRight.setLayoutParams(paramsProgressBarRight)
            lessonInfoContainer.setLayoutParams(paramsLessonInfoContainer)
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
        if (progressToLessons != null && progressToLessons!!.size > position) {
            holder.bind(lessons[position], progressToLessons!![position])
        } else holder.bind(lessons[position])

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
        if (list != null) notifyDataSetChanged()
    }

    fun addLesson(lessonsList: Lessons?){
        if (lessonsList != null) {
            lessons = lessonsList
            notifyDataSetChanged()
        }
    }
}