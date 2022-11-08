package com.example.schoolandroid.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolandroid.adapter.PageAdapter
import com.example.schoolandroid.databinding.ActivityMainBinding
import com.example.schoolandroid.screens.course.about_course
import com.example.schoolandroid.screens.course.chats
import com.example.schoolandroid.screens.course.course
import com.google.android.material.tabs.TabLayoutMediator

class CourseActivity : AppCompatActivity() {

    private val fragList = listOf(
        about_course.newInstance(),
        course.newInstance(),
        chats.newInstance()
    )

    private val fragNames = listOf(
        "о курсе",
        "урок",
        "чаты"
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PageAdapter(this, fragList)
        binding.mainActivityVp.adapter = adapter

        TabLayoutMediator(binding.mainPageBottomMenue, binding.mainActivityVp){
                tab, pos -> tab.text = fragNames[pos]
        }.attach()

        val intent : Intent = getIntent()
        println(intent.getIntExtra(MainActivity.extrahui, 100))
    }
}