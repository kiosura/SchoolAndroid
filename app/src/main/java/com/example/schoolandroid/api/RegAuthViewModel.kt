package com.example.schoolandroid.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Call

class RegAuthViewModel : ViewModel() {
    val retrofitApi = RetrofitInstance.api

    var list : MutableLiveData<Call<PostRegUser>> = MutableLiveData()

    class PostRegUser(loginValue : String, passwordValue : String) {
        @SerializedName("email")
        @Expose
        private var email : String = loginValue

        @SerializedName("password")
        @Expose
        private var password : String = passwordValue

    }
    fun postRegistration(loginInputValue : String, passwordInputValue : String) : MutableLiveData<Call<PostRegUser>> {
//        retrofitApi.sendRegistration(PostRegUser(loginInputValue, passwordInputValue))
        viewModelScope.launch {
            list.value = retrofitApi.sendRegistration(PostRegUser(loginInputValue, passwordInputValue))
        }
        return list
    }

}