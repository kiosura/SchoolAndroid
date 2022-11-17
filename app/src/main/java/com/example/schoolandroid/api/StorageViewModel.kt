package com.example.schoolandroid.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.Courses
import kotlinx.coroutines.launch
import retrofit2.Response

class StorageViewModel : ViewModel() {
    var retrofitApi = RetrofitInstance.api
    val coursesList : MutableLiveData<Response<Courses>> = MutableLiveData()

    fun getCourses() : MutableLiveData<Response<Courses>> {
        viewModelScope.launch {
            coursesList.value = retrofitApi.fetchCourses()
        }
        return coursesList
    }
}