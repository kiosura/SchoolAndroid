package com.example.schoolandroid.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolandroid.adapter.PageAdapter
import com.example.schoolandroid.databinding.ActivityCourseBinding
import com.example.schoolandroid.dialogs.PushDialog
import com.example.schoolandroid.dialogs.SettingsDialog
import com.example.schoolandroid.screens.course.about_course
import com.example.schoolandroid.screens.course.chats
import com.example.schoolandroid.screens.course.lesson
import com.google.android.material.tabs.TabLayoutMediator

class CourseActivity : AppCompatActivity() {

    private val fragList = listOf(
        about_course.newInstance(),
        lesson.newInstance(),
        chats.newInstance()
    )

    private val fragNames = listOf(
        "о курсе",
        "урок",
        "чаты"
    )

    private lateinit var binding: ActivityCourseBinding

    private val PushDialogFragment = PushDialog()
    private val SettingsDialogFragment = SettingsDialog()
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PageAdapter(this, fragList)
        binding.courseActivityVp.adapter = adapter

        TabLayoutMediator(binding.coursePageBottomMenue, binding.courseActivityVp){
                tab, pos -> tab.text = fragNames[pos]
        }.attach()

        val intent : Intent = getIntent()
        println(intent.getIntExtra(MainActivity.extrahui, 100))

        val buttonP = binding.pushButton
        buttonP.setOnClickListener {
            PushDialogFragment.show(manager, "Push")
        }

        val buttonS = binding.settingsButton
        buttonS.setOnClickListener {
            SettingsDialogFragment.show(manager, "Settings")
        }
    }
}