package com.example.schoolandroid.storage

import androidx.lifecycle.MutableLiveData
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.screens.main.courses
import retrofit2.Response

object Storage {
    private var coursesList : MutableLiveData<Courses> = MutableLiveData()

    fun addCourses(list : MutableLiveData<Response<Courses>>?) {
        coursesList = MutableLiveData(list?.value?.body())
    }

    fun getCourses() : MutableLiveData<Courses> {
        return coursesList
    }
}