package com.example.schoolandroid.api

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.schoolandroid.data.RetrofitPostRequest

import com.example.schoolandroid.interfaces.FragmentReplacer
import com.example.schoolandroid.screens.main.profile
import com.example.schoolandroid.storage.Storage

import kotlinx.coroutines.launch


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

                if (responseUser?.error_message == null) {
                    fragmentReplacer.replace(2, profile())
                }

            } catch (e: Exception) {
                Log.e("TAG", "Exception during request -> ${e.localizedMessage}")
            }
        }
    }
}