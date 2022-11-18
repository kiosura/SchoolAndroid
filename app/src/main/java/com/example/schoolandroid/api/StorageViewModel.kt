package com.example.schoolandroid.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.Lessons
import kotlinx.coroutines.launch
import retrofit2.Response

class StorageViewModel : ViewModel() {
    var retrofitApi = RetrofitInstance.api

    // перенести инициализацию полей в методы get
    val coursesList : MutableLiveData<Response<Courses>> = MutableLiveData()
    val list : MutableLiveData<Response<Courses>> = MutableLiveData()

    fun getCourses() : MutableLiveData<Response<Courses>> {
        viewModelScope.launch {
            coursesList.value = retrofitApi.fetchCourses()
        }
        return coursesList
    }

    fun getLessons() : MutableLiveData<Response<Courses>> {
        viewModelScope.launch {
            list.value = retrofitApi.fetchLessons()
        }
        return list
    }
}