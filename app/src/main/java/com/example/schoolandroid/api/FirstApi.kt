package com.example.schoolandroid.api

import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await

interface FirstApi {

    companion object {
        private fun client() : OkHttpClient = OkHttpClient.Builder().build()
        private fun url() : String = "https://habr.com/ru/company/yandex_praktikum/blog/561696/"

        suspend fun apiBase(): String {
            val request = Request.Builder()
                .url(url())
                .build()

            val response = client().newCall(request).await()
            return response.body()!!.string()
        }
    }
}