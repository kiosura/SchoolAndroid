package com.example.schoolandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.schoolandroid.adapter.MainPageAdapter
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.databinding.ActivityMainBinding
import com.example.schoolandroid.dialogs.PushDialog
import com.example.schoolandroid.dialogs.SettingsDialog
import com.example.schoolandroid.storage.Storage
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    // tablayout items' names
    private val fragNames = listOf(
        "о школе",
        "курсы",
        "профиль"
    )

    // headers' names
    private val baseNames : ArrayList<String> = arrayListOf<String>(
        "О Школе",
        "Все курсы",
        "Профиль"
    )

    private lateinit var binding: ActivityMainBinding
    private val baseAdapter = MainPageAdapter(this)

//    private val PushDialogFragment = PushDialog()
//    private val SettingsDialogFragment = SettingsDialog()
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // connecting views
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MainActivity adapter connect
        binding.mainActivityVp.adapter = baseAdapter

        // tablayout's viewpager manager
        TabLayoutMediator(binding.mainPageBottomMenue, binding.mainActivityVp){
            tab, pos -> tab.text = fragNames[pos]
        }.attach()

        // push button, calls push dialog
        val buttonP = binding.pushButton
        buttonP.setOnClickListener {
            PushDialog().show(manager, "Push")
        }

        // settings button, calls settings dialog
        val buttonS = binding.settingsButton
        buttonS.setOnClickListener {
            SettingsDialog().show(manager, "Settings")
        }

        tabClickListener()

        // custom view model for main activity
        val storageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        // subscription for MutableLiveData<Response<Courses>> changes - coming from API
        val coursesRaw = storageViewModel.getCourses()
        coursesRaw.observe(this@MainActivity) {
            Storage.addCourses(coursesRaw)
            val lessonsRaw = storageViewModel.getLessons()
            lessonsRaw.observe(this@MainActivity) {
                Storage.mergeCourses(lessonsRaw)
            }
        }
    }

    suspend fun hard(){

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

