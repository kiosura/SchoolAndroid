package com.example.schoolandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import com.example.schoolandroid.adapter.PageAdapter
import com.example.schoolandroid.databinding.ActivityMainBinding
import com.example.schoolandroid.screens.about_school
import com.example.schoolandroid.screens.courses
import com.example.schoolandroid.screens.profile
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private val fraglist = listOf(
        about_school.newInstance(),
        courses.newInstance(),
        profile.newInstance()
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PageAdapter(this, fraglist)
        binding.mainActivityVp.adapter = adapter

        TabLayoutMediator(binding.mainPageBottomMenue, binding.mainActivityVp){
            tab, pos ->
        }.attach()

    }
}