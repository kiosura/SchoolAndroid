package com.example.schoolandroid.api

import com.example.schoolandroid.data.CourseX
import retrofit2.Response

class RetrofitApiImplementation {

    suspend fun getCourses(): Response<CourseX>{
        return RetrofitInstance.api.fetchCourses()
    }

}