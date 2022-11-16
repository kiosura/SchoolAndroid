package com.example.schoolandroid.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.Course
import retrofit2.Response
import kotlin.math.log


//class CourseViewModel(application: Application): AndroidViewModel(application) {
//
//    fun fetchCourses(retrofitApi: RetrofitApi){
//        viewModelScope.launch {
//            retrofitApi.fetchCourses()
//        }
//    }
//}

class CourseViewModel: ViewModel(){
    var retrofitApi = RetrofitInstance.api
    val coursesList: MutableLiveData<Response<Course>> = MutableLiveData()

    fun getCourses(): MutableLiveData<Response<Course>> {
        viewModelScope.launch {
            coursesList.value = retrofitApi.fetchCourses()
        }
        return coursesList
    }
}



