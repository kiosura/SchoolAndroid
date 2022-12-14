package com.example.schoolandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.schoolandroid.adapter.MainPageAdapter
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.data.User
import com.example.schoolandroid.databinding.ActivityMainBinding
import com.example.schoolandroid.dialogs.PushDialog
import com.example.schoolandroid.dialogs.SettingsDialog
import com.example.schoolandroid.screens.main.about_school
import com.example.schoolandroid.screens.main.courses
import com.example.schoolandroid.screens.main.profile
import com.example.schoolandroid.screens.main.regAuth
import com.example.schoolandroid.storage.PersistentStorage
import com.example.schoolandroid.storage.Storage
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    // tablayout items' names
    private val fragNames = listOf(
        "главная",
        "курсы",
        "профиль"
    )

    // headers' names
    private val baseNames : ArrayList<String> = arrayListOf(
        "Главная",
        "Все курсы",
        "Профиль"
    )

    private lateinit var binding: ActivityMainBinding
    private lateinit var baseAdapter : MainPageAdapter

    // custom view models for main activity
    private lateinit var storageViewModel : StorageViewModel

//    private val PushDialogFragment = PushDialog()
//    private val SettingsDialogFragment = SettingsDialog()
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        // SharedPref initial
        PersistentStorage.init(this)
        val user = PersistentStorage.getObject<User>()

        // checking if user already exists, saving it in Storage if it does
        // and changing fragment (profile -> regAuth) if it doesn't
        val listOfFragments = arrayListOf(about_school(), courses(), profile())
        if (user.registered_datetime == null) listOfFragments[2] = regAuth()
        else {
            Storage.setUser(user, false)
            storageViewModel.getProgresses()
        }

        lifecycleScope.launch(Dispatchers.Main) {
            async { storageViewModel.getCourses()
                storageViewModel.getMyCourses()  }.await()
            async { storageViewModel.getLessons()
                storageViewModel.getChats()
                storageViewModel.getMyChats() }.await()
        }

        baseAdapter = MainPageAdapter(this, listOfFragments.toList())

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

        // subscription for MutableLiveData<Response<Courses>> changes - coming from API
//        val coursesRaw = storageViewModel.getCourses()
//        coursesRaw.observe(this) {
//            if (coursesRaw.value!!.body() != null) {
//                Storage.addCourses(coursesRaw)
//                val lessonsRaw = storageViewModel.getLessons()
//                lessonsRaw.observe(this) {
//                    Storage.mergeCourses(lessonsRaw)
//                }
//            }
//        }
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

