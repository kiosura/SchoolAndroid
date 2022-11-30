package com.example.schoolandroid.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolandroid.R
import com.example.schoolandroid.activity.CourseActivity
import com.example.schoolandroid.adapter.recycleview.ProfileCoursesAdapter
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.data.User
import com.example.schoolandroid.databinding.FragmentProfileBinding
import com.example.schoolandroid.interfaces.Listener
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.PersistentStorage
import com.example.schoolandroid.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class profile(val isAuth : Boolean = false): BaseFragment(R.layout.fragment_profile), Listener {

    private lateinit var recycleView: RecyclerView

    private lateinit var storageViewModel: StorageViewModel

    private var profileAdapter: ProfileCoursesAdapter = ProfileCoursesAdapter(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storageViewModel = ViewModelProvider(requireActivity()).get(StorageViewModel::class.java)
        if (isAuth) lifecycleScope.launch {
            async {
                storageViewModel.getCourses()
                storageViewModel.getMyCourses()
            }.await()
            async {
                storageViewModel.getLessons()
                storageViewModel.getChats()
                storageViewModel.getMyChats()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // needs to open UserObserver
        val binding = FragmentProfileBinding.bind(view)
        fun bind() = with(binding) {
            val user = Storage.getUser().value
            userName.text = if (user?.name != "") user?.name + " " + user?.surname else "unknown"
            userLogin.text = if (user?.email != "") user?.email else user.phone_number
            recycleCoursesProfile.adapter = profileAdapter
            userLogout.setOnClickListener {
                PersistentStorage.logoutUser()
                Storage.setUser(User())
                storageViewModel.logoutGetData()
                fragmentReplacer.replace(2, regAuth())
            }
        }

        coursesProfileObserver()
        bind()
    }

    fun coursesProfileObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            Storage.getCourses(isMy = true).observe(requireActivity()) { courses ->
                profileAdapter.addCourse(courses)
            }

        }
    }

    override fun onClick(position: Int) {
        Storage.setCurrent(position, isMy = true)
        val intent: Intent = Intent(context, CourseActivity::class.java)
        context?.startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = profile()
    }
}