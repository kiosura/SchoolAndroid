package com.example.schoolandroid.api
import okhttp3.logging.HttpLoggingInterceptor
import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanseTest : Application() {

    companion object {
        private const val header_name : String = "Authorization"
        private const val api_key : String = "BF7nDuG4.ZTGSsdUsnqSIk9ab2psqtEKrx19Pfkyb"
    }

    lateinit var retrofitApi: RetrofitApi


    override fun onCreate(){
        super.onCreate()

        configureRetrofit()

    }

    private fun configureRetrofit() {
        val httploggingInterceptor = HttpLoggingInterceptor()
        httploggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{chain ->
                val request = chain.request().newBuilder()
                    .addHeader(header_name, api_key)
                    .build()

                return@addInterceptor chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kofefast.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitApi = retrofit.create(RetrofitApi::class.java)
    }

}