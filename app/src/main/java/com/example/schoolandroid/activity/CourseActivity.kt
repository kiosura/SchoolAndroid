package com.example.schoolandroid.activity

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
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
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CourseActivity : AppCompatActivity() {
//    companion object {
//        const val extrahui : String = "EXTRAHUI"
//    }

    private var course_id : Int = 0

    private val fragNames = listOf(
        "о курсе",
        "урок",
        "чаты"
    )

    private val baseNames : ArrayList<String> = arrayListOf(
        "О курсе",
        "Урок",
        "Чаты"
    )



    private lateinit var binding: ActivityCourseBinding

    private val PushDialogFragment = PushDialog()
    private val SettingsDialogFragment = SettingsDialog()
    private val manager = supportFragmentManager
    private val baseAdapter = CoursePageAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        course_id = getIntent().getIntExtra(MainActivity.extrahui, 100)

        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = baseAdapter
        binding.courseActivityVp.adapter = adapter

        TabLayoutMediator(binding.coursePageBottomMenue, binding.courseActivityVp){
                tab, pos -> tab.text = fragNames[pos]
        }.attach()

        tabClickListener()


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
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        var isItTrue : Boolean = false
        baseAdapter.mapOfFragment.values.forEach {
            if (it.pageId.toInt() == R.layout.task_view) {
                isItTrue = true
            }
            else return@forEach
        }
        if (isItTrue) {
            val tabLayout : TabLayout = findViewById<TabLayout>(R.id.coursePageBottomMenue)
            val tab = tabLayout.getTabAt(1)
            if (tab!!.isSelected) {
                baseAdapter.replaceDef(1)
                binding.textView.text = "Урок"
            }
            else finish()
        }
        else finish()
    }

    fun tabClickListener(){
        binding.coursePageBottomMenue.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        binding.textView.text = baseNames[0]
                    }
                    1 -> {
                        binding.textView.text = baseNames[1]
                    }
                    2 -> {
                        binding.textView.text = baseNames[2]
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    1 -> {
                        baseNames[1] = binding.textView.text.toString()
                    }
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}