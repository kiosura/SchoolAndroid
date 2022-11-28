package com.example.schoolandroid.screens.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.schoolandroid.R
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.data.User
import com.example.schoolandroid.databinding.FragmentProfileBinding
import com.example.schoolandroid.screens.BaseFragment
import com.example.schoolandroid.storage.PersistentStorage
import com.example.schoolandroid.storage.Storage


class profile(val isAuth : Boolean = false): BaseFragment(R.layout.fragment_profile) {

    private lateinit var storageViewModel: StorageViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storageViewModel = ViewModelProvider(requireActivity()).get(StorageViewModel::class.java)
        if (isAuth) {
            storageViewModel.getCoursesWithLessons()
            storageViewModel.getMyCourses()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)
        fun bind() = with(binding) {
            val user = Storage.getUser().value
            userName.text = if (user?.name != "") user?.name + " " + user?.surname else "unknown"
            userLogin.text = if (user?.email != "") user?.email else user.phone_number
            userLogout.setOnClickListener {
                PersistentStorage.logoutUser()
                Storage.setUser(User())
                fragmentReplacer.replace(2, regAuth())
            }
        }
        bind()
    }

    companion object {
        @JvmStatic
        fun newInstance() = profile()
    }
}