package com.example.schoolandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolandroid.R
import com.example.schoolandroid.adapter.CoursePageAdapter
import com.example.schoolandroid.databinding.ActivityCourseBinding
import com.example.schoolandroid.dialogs.PushDialog
import com.example.schoolandroid.dialogs.SettingsDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CourseActivity : AppCompatActivity() {

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

    private val manager = supportFragmentManager
    private val baseAdapter = CoursePageAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.courseActivityVp.adapter = baseAdapter

        TabLayoutMediator(binding.coursePageBottomMenue, binding.courseActivityVp){
                tab, pos -> tab.text = fragNames[pos]
        }.attach()

        tabClickListener()

        val buttonP = binding.pushButton
        buttonP.setOnClickListener {
            val PushDialogFragment = PushDialog()
            PushDialogFragment.show(manager, "Push")
        }

        val buttonS = binding.settingsButton
        buttonS.setOnClickListener {
            val SettingsDialogFragment = SettingsDialog()
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
                baseAdapter.replaceDefault(1)
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