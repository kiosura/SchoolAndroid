package com.example.schoolandroid

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.adapter.CourseAdapter
import com.example.schoolandroid.adapter.PageAdapter
import com.example.schoolandroid.data.Course
import com.example.schoolandroid.databinding.ActivityMainBinding
import com.example.schoolandroid.screens.about_school
import com.example.schoolandroid.screens.courses
import com.example.schoolandroid.screens.profile
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private val fragList = listOf(
        about_school.newInstance(),
        courses.newInstance(),
        profile.newInstance()
    )

    private val fragNames = listOf(
        "о школе",
        "курсы",
        "профиль"
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

        var pushLayout = binding.viewStub2
        var pushWindow = pushLayout.inflate()
        pushLayout.isVisible = false

        val pushOpen = binding.pushButton
        pushOpen.setOnClickListener{
            pushWindow.isVisible = true


        }
        val pushClose = findViewById<Button>(R.id.closepush)
        println(pushClose.text)

        pushClose.setOnClickListener{
            pushWindow.isVisible = false

        }



    }
}