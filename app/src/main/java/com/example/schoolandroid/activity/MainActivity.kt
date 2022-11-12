package com.example.schoolandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolandroid.adapter.PageAdapter
import com.example.schoolandroid.databinding.ActivityMainBinding
import com.example.schoolandroid.dialogs.PushDialog
import com.example.schoolandroid.dialogs.SettingsDialog
import com.example.schoolandroid.screens.main.about_school
import com.example.schoolandroid.screens.main.courses
import com.example.schoolandroid.screens.main.profile
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    companion object {
        const val extrahui : String = "hui"
    }

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

    private val baseNames : ArrayList<String> = arrayListOf<String>(
        "О Школе",
        "Все курсы",
        "Профиль"
    )

    private lateinit var binding: ActivityMainBinding

    private val PushDialogFragment = PushDialog()
    private val SettingsDialogFragment = SettingsDialog()
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PageAdapter(this, fragList)
        binding.mainActivityVp.adapter = adapter

        TabLayoutMediator(binding.mainPageBottomMenue, binding.mainActivityVp){
            tab, pos -> tab.text = fragNames[pos]
        }.attach()

        val buttonP = binding.pushButton
        buttonP.setOnClickListener {
            PushDialogFragment.show(manager, "Push")
        }

        val buttonS = binding.settingsButton
        buttonS.setOnClickListener {
            SettingsDialogFragment.show(manager, "Settings")
        }

        tabClickListener()
    }

    fun tabClickListener(){
        binding.mainPageBottomMenue.addOnTabSelectedListener(object : OnTabSelectedListener {
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

