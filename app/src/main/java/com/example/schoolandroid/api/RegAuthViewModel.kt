package com.example.schoolandroid.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.RetrofitPostRequest
import com.example.schoolandroid.data.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.POST

class RegAuthViewModel : ViewModel() {
    val retrofitApi = RetrofitInstance.api

    fun postRegistration(loginInputValue : String, passwordInputValue : String) {
        viewModelScope.launch {
            try {
                retrofitApi.sendRegistration(
                    RetrofitPostRequest(
                        login = loginInputValue,
                        password = passwordInputValue,
                        password_complete = passwordInputValue
                    )
                )
            }
            catch (e: Exception) {
                Log.e("TAG", "Exception during request -> ${e.localizedMessage}")
            }
        }

//        var a : Map<String, String>
//        a = mapOf(
//            "login" to loginInputValue,
//            "password" to passwordInputValue
//        )
//        viewModelScope.launch {
//            try {
//                retrofitApi.sendRegistration(a)
//            }
//            catch (e: Exception) {
//                Log.e("TAG", "Exception during request -> ${e.localizedMessage}")
//            }
//        }

    }

}