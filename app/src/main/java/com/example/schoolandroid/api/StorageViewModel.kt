package com.example.schoolandroid.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.*
import com.example.schoolandroid.interfaces.FragmentReplacer
import com.example.schoolandroid.screens.main.profile
import com.example.schoolandroid.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

class StorageViewModel : ViewModel() {
    var retrofitApi = RetrofitInstance.api

    suspend fun getMyCourses() {
        try {
            if (Storage.getUser().value?.registered_datetime != null) {
                val myselfCoursesList = retrofitApi.fetchMyCourses(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                Storage.addCourses(myselfCoursesList, isMy = true)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception during request getCourses -> ${e.localizedMessage}")
        }
    }

    suspend fun getCourses() {
        try {
            val coursesList: Courses?
            if (Storage.getUser().value?.registered_datetime != null) {
                coursesList = retrofitApi.fetchCoursesPost(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                Storage.addCourses(coursesList)
            }
            else {
                coursesList = retrofitApi.fetchCourses().body()
                Storage.addCourses(coursesList)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception during request getMyCourses -> ${e.localizedMessage}")
        }
    }


    suspend fun getLessons() {
        try {
            val lessonsFromCourses : Courses?
            if (Storage.getUser().value?.registered_datetime != null) {
                lessonsFromCourses = retrofitApi.fetchLessonsPost(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                Storage.mergeCourses(lessonsFromCourses)
            }
            else {
                lessonsFromCourses = retrofitApi.fetchLessons().body()
                Storage.mergeCourses(lessonsFromCourses)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception during request getLessons -> ${e.localizedMessage}")
        }
    }

    suspend fun getChats() {
        try {
            val chatsFromCourses : Courses?
            if (Storage.getUser().value?.registered_datetime != null) {
                chatsFromCourses = retrofitApi.fetchChatsPost(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                Storage.mergeCourses(chatsFromCourses)
            }
            else {
                chatsFromCourses = retrofitApi.fetchChats().body()
                Storage.mergeCourses(chatsFromCourses)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception during request getChats -> ${e.localizedMessage}")
        }
    }

    suspend fun getMyChats() {
        try {
            if (Storage.getUser().value?.registered_datetime != null) {
                val chatsFromCourses = retrofitApi.fetchMyChats(
                    RetrofitUserIdPostRequest(
                        Storage.getUser().value!!.id!!
                    )
                ).body()

                Storage.mergeCourses(chatsFromCourses, isMy = true)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception during request getMyChats -> ${e.localizedMessage}")
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
                Log.e("TAG", "Exception during request postRegistration -> ${e.localizedMessage}")
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
                    fragmentReplacer.replace(2, profile(isAuth = true))
                }

            } catch (e: Exception) {
                Log.e("TAG", "Exception during request postAuthentification -> ${e.localizedMessage}")
            }
        }
    }

    fun postUpdateUser(user_id : Int, name : String, surname : String, email : String, phone_number : String) {
        viewModelScope.launch {
            try {
                val responseUser = retrofitApi.updateUser(
                    RetrofitUpdateUserPostRequest(
                        user_id = user_id,
                        name = name,
                        surname = surname,
                        email = email,
                        phone_number = phone_number
                    )
                ).body()

                responseUser?.let { Storage.setUser(it) }
            }
            catch (e: Exception) {
                Log.e("TAG", "Exception during request postUpdateUser -> ${e.localizedMessage}")
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
                    Log.e("TAG", "Exception during request getProgresses -> ${e.localizedMessage}")
                }
            }
        }
    }

    fun logoutGetData(){
        viewModelScope.launch {
            try {
                async {
                    getCourses()
                    Storage.addCourses(null, isMy = true)
                }.await()
                async {
                    getLessons()
                    getChats()
                }
            } catch (e: Exception) {
                Log.e("TAG", "Exception during logout -> ${e.localizedMessage}")
            }
        }
    }
}