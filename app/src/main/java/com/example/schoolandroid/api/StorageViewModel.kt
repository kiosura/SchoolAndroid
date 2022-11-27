package com.example.schoolandroid.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.RetrofitUserIdPostRequest
import com.example.schoolandroid.data.RetrofitUserPostRequest
import com.example.schoolandroid.data.User
import com.example.schoolandroid.interfaces.FragmentReplacer
import com.example.schoolandroid.screens.main.profile
import com.example.schoolandroid.storage.Storage
import kotlinx.coroutines.launch
import retrofit2.Response

class StorageViewModel : ViewModel() {
    var retrofitApi = RetrofitInstance.api

    fun getCoursesWithLessons() {
        viewModelScope.launch {
            val coursesList : Courses?
            val lessonsFromCourses : Courses?
            if (Storage.getUser().value?.registered_datetime != null) {
                coursesList = retrofitApi.fetchCoursesPost(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                coursesList?.let { Storage.addCourses(it) }

                lessonsFromCourses = retrofitApi.fetchLessonsPost(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                Storage.mergeCourses(lessonsFromCourses)
            }
            else {
                coursesList = retrofitApi.fetchCourses().body()
                coursesList?.let { Storage.addCourses(it) }

                lessonsFromCourses = retrofitApi.fetchLessons().body()
                Storage.mergeCourses(lessonsFromCourses)
            }
        }
    }

    fun getMyCourses() {
        viewModelScope.launch {
            if (Storage.getUser().value?.registered_datetime != null) {
                val coursesList = retrofitApi.fetchMyCourses(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                coursesList?.let { Storage.addCourses(it, isMy = true) }
            }
        }
    }

    fun postRegistration(fragmentReplacer : FragmentReplacer, loginInputValue : String, passwordInputValue : String) {
        viewModelScope.launch {
            try {
                val responseUser = retrofitApi.sendRegistration(
                    RetrofitUserPostRequest(
                        login = loginInputValue,
                        password = passwordInputValue
                    )
                ).body()

                responseUser?.let { Storage.setUser(it) }

                if (responseUser?.error_message == null) {
                    fragmentReplacer.replace(2, profile())
                }

            } catch (e: Exception) {
                Log.e("TAG", "Exception during request -> ${e.localizedMessage}")
            }
        }
    }

    fun postAuthentification(fragmentReplacer : FragmentReplacer, loginInputValue : String, passwordInputValue : String) {
        viewModelScope.launch {
            try {
                val responseUser = retrofitApi.authentication(
                    RetrofitUserPostRequest(
                        login = loginInputValue,
                        password = passwordInputValue
                    )
                ).body()

                responseUser?.let { Storage.setUser(it) }

                if (responseUser?.error_message == null) {
                    getCoursesWithLessons()
                    getMyCourses()
                    fragmentReplacer.replace(2, profile())
                }

            } catch (e: Exception) {
                Log.e("TAG", "Exception during request -> ${e.localizedMessage}")
            }
        }
    }

    fun getProgresses() {
        if (Storage.getUser().value?.id != null) {
            viewModelScope.launch {
                try {
                    val responseUserProgresses = retrofitApi.userProgresses(
                        RetrofitUserIdPostRequest(
                            user_id = Storage.getUser().value!!.id!!,
                        )
                    ).body()

                    if (responseUserProgresses?.error_message == null) {
                        Storage.setProgresses(responseUserProgresses!!.progresses!!)
                    }

                } catch (e: Exception) {
                    Log.e("TAG", "Exception during request -> ${e.localizedMessage}")
                }
            }
        }
    }
}