package com.example.schoolandroid.api

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.schoolandroid.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.CourseX
import retrofit2.Response
import com.example.schoolandroid.api.RetrofitApi



//class CourseViewModel(application: Application): AndroidViewModel(application) {
//
//    fun fetchCourses(retrofitApi: RetrofitApi){
//        viewModelScope.launch {
//            retrofitApi.fetchCourses()
//        }
//    }
//}

class CourseViewModel: ViewModel(){
    var retrofitApi = RetrofitApiImplementation()
    val coursesList: MutableLiveData<Response<CourseX>> = MutableLiveData()

    fun getCourses(){
        viewModelScope.launch {
            coursesList.value = retrofitApi.getCourses()
        }
    }
}



