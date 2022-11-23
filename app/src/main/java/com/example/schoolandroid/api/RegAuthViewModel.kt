package com.example.schoolandroid.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.data.Courses
import com.example.schoolandroid.data.RetrofitPostRequest
import com.example.schoolandroid.data.User
import com.example.schoolandroid.interfaces.FragmentReplacer
import com.example.schoolandroid.screens.main.profile
import com.example.schoolandroid.storage.Storage
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.POST
import java.io.IOException

class RegAuthViewModel : ViewModel() {
    val retrofitApi = RetrofitInstance.api

    fun postRegistration(fragmentReplacer : FragmentReplacer, loginInputValue : String, passwordInputValue : String) {
        viewModelScope.launch {
            try {
                val responseUser = retrofitApi.sendRegistration(
                    RetrofitPostRequest(
                        login = loginInputValue,
                        password = passwordInputValue
                    )
                ).body()
                Storage.setUser(responseUser)
                fragmentReplacer.replace(2, profile())

            } catch (e: Exception) {
                Log.e("TAG", "Exception during request -> ${e.localizedMessage}")
            }
        }
    }
}