package com.example.schoolandroid.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.RetrofitLessonRequest
import com.example.schoolandroid.data.RetrofitTaskAnswerRequest
import com.example.schoolandroid.storage.Storage
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {
    private val retrofitApi = RetrofitInstance.api
    internal var lessonIndex : Int = 0
        get() = field
        set(value) { field = value }

    fun getLesson() {
        viewModelScope.launch {
            try {
                val lesson = retrofitApi.fetchLesson(
                    RetrofitLessonRequest(
                        course_id = Storage.getCurrentCourse().value!!.id,
                        lesson_index = lessonIndex
                    )
                ).body()
                Storage.updateLesson(lesson, lessonIndex)
            } catch (e: Exception) {
                Log.e("TAG", "Exception during request getLesson -> ${e.localizedMessage}")
            }
        }
    }

    fun postAnswer(text : String, taskIndex : Int, storageVM: StorageViewModel) {
        viewModelScope.launch {
            try {
                val answer = retrofitApi.fetchTaskAnswer(
                    RetrofitTaskAnswerRequest(
                        course_id = Storage.getCurrentCourse().value!!.id,
                        lesson_index = lessonIndex,
                        task_index = taskIndex,
                        user_id = Storage.getUser().value!!.id!!,
                        answer = text
                    )
                ).body()
                if (answer != null) {
                    Storage.setProgresses(answer.progresses!!)
                    storageVM.loginGetData()
                }
            } catch (e: Exception) {
                Log.e("TAG", "Exception during request postAnswer -> ${e.localizedMessage}")
            }
        }
    }

}