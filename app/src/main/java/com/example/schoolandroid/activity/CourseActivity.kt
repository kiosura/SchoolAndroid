package com.example.schoolandroid.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolandroid.R

class CourseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        val intent : Intent = getIntent()
        println(intent.getIntExtra(MainActivity.extrahui, 100))
    }
}