package com.example.schoolandroid.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.RetrofitLessonRequest
import com.example.schoolandroid.data.RetrofitTaskAnswerRequest
import com.example.schoolandroid.storage.Storage
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {
    private val retrofitApi = RetrofitInstance.api
    internal var lessonIndex : Int = 0
        get() = field
        set(value) { field = value }

    fun getLesson() {
        viewModelScope.launch {
            val lesson = retrofitApi.fetchLesson(
                RetrofitLessonRequest(
                    course_id = Storage.getCurrentCourse().value!!.id,
                    lesson_index = lessonIndex
                )
            ).body()
            Storage.updateLesson(lesson)
        }
    }

    fun postAnswer(text : String, taskIndex : Int) {
        viewModelScope.launch {
            val answer = retrofitApi.fetchTaskAnswer(
                RetrofitTaskAnswerRequest(
                    course_id = Storage.getCurrentCourse().value!!.id,
                    lesson_index = lessonIndex,
                    task_index = taskIndex,
                    user_id = Storage.getUser().value!!.id!!,
                    answer = text
                )
            ).body()
            if (answer != null)
                Storage.setProgresses(answer.progresses!!)
        }
    }

}