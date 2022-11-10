package com.example.schoolandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.CoursePageAdapter
import com.example.schoolandroid.adapter.recycleview.AboutCourseLessonsAdapter
import com.example.schoolandroid.data.Lesson
import com.example.schoolandroid.databinding.ActivityCourseBinding
import com.example.schoolandroid.dialogs.PushDialog
import com.example.schoolandroid.dialogs.SettingsDialog
import com.example.schoolandroid.screens.course.about_course
import com.example.schoolandroid.screens.course.chats
import com.example.schoolandroid.screens.course.lesson
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CourseActivity : AppCompatActivity() {
    companion object {
        const val extrahui : String = "EXTRAHUI"
    }

    private var course_id : Int = 0

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
        course_id = getIntent().getIntExtra(MainActivity.extrahui, 100)

        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoursePageAdapter(this)
        binding.courseActivityVp.adapter = adapter

        TabLayoutMediator(binding.coursePageBottomMenue, binding.courseActivityVp){
                tab, pos -> tab.text = fragNames[pos]
        }.attach()

//        val tabLayout : TabLayout = findViewById<TabLayout>(R.id.coursePageBottomMenue)
//        val tab = tabLayout.getTabAt(1)
//        tab?.select()

        val buttonP = binding.pushButton
        buttonP.setOnClickListener {
            PushDialogFragment.show(manager, "Push")
        }

        val buttonS = binding.settingsButton
        buttonS.setOnClickListener {
            SettingsDialogFragment.show(manager, "Settings")
        }

        val buttonBack = binding.backtoMain
        buttonBack.setOnClickListener {
            finish()
        }
    }

}