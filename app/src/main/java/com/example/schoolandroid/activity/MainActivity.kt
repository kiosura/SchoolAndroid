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
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    companion object {
        public const val extrahui : String = "hui"
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
            SettingsDialogFragment.show(manager, "Push")
        }

    }
}